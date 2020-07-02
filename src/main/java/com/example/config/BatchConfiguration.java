package com.example.config;

import com.example.listener.PaymentProcessingJobExecutionListener;
import com.example.model.Payment;
import com.example.model.mapper.PaymentPrepareStatementSetter;
import com.example.model.mapper.PaymentRowMapper;
import com.example.processor.PaymentProcessingItemProcessor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Payment> reader() {
        JdbcCursorItemReader<Payment> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM payments");
        reader.setRowMapper(new PaymentRowMapper());

        return reader;
    }

    @Bean
    public PaymentProcessingItemProcessor processor(){
        return new PaymentProcessingItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Payment> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Payment>()
            .itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>()
            )
            .sql("UPDATE PAYMENTS SET paymentType = ?, " +
                 "paymentStatus = ?, " +
                 "description = ? " +
                 "WHERE paymentId = ?"
            )
            .itemPreparedStatementSetter(new PaymentPrepareStatementSetter())
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Step updatePaymentStatusStep(
        ItemReader<Payment> reader, ItemWriter<Payment> writer,
        ItemProcessor<Payment, Payment> processor) {
     return
         stepBuilderFactory
             .get("updatePaymentStatusStep")
             .<Payment, Payment> chunk(10)
             .reader(reader)
             .processor(processor)
             .writer(writer)
             .taskExecutor(taskExecutor())
             .build();
    }

      @Bean
      public Job exportProcessAndUpdatePaymentJob(PaymentProcessingJobExecutionListener listener, Step updatePaymentStatusStep) {
       return jobBuilderFactory.get("processAndUpdatePaymentJob")
                               .incrementer(new RunIdIncrementer())
                               .listener(listener)
                               .flow(updatePaymentStatusStep)
                               .end()
                               .build();
      }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(2);
        /*taskExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        }, 8000);*/
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }



}