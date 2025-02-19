package io.github.kongyu666.spark.task.core;

import cn.hutool.extra.spring.SpringUtil;
import io.github.kongyu666.spark.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

/**
 * SparkCore相关任务
 *
 * @author 孔余
 * @since 2024-02-28 11:35
 */
@Component
@Slf4j
public class MySparkCoreTask {

    /**
     * RDD转Dataset
     */
    public void run() {
        // 获取环境
        JavaSparkContext sc = SpringUtil.getBean("sparkContext", JavaSparkContext.class);
        SparkSession spark = SpringUtil.getBean("sparkSession", SparkSession.class);

        // 读取文本文件，创建JavaRDD
        JavaRDD<String> textFileRDD = sc.textFile("hdfs://bigdata01:8020/data/my_table_user.csv");

        // 将RDD中的数据映射到Row对象
        JavaRDD<User> userRDD = textFileRDD.map(User::fromCsvString);

        // 创建Dataset
        Dataset<Row> ds = spark.createDataFrame(userRDD, User.class);

        // 显示Dataset的内容
        ds.show();
        System.out.println("sparkDataset任务运行成功");
    }
}
