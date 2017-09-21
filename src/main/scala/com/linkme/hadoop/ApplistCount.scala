package com.linkme.hadoop

import java.io.IOException
import java.lang
import java.util.StringTokenizer

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}

/**
  * Created by linkedmemuller on 2017/9/21.
  */
class ApplistCount{

}
object ApplistCount {

  object AppMaper {
    val one =new IntWritable(1)

  }
  class  AppMaper extends  Mapper[Object,Text,Text ,IntWritable]{
    var word =new Text()

    @throws[IOException]
    @throws[InterruptedException]
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val str:  StringTokenizer =new StringTokenizer(value.toString)
      if(str.hasMoreTokens){
        word.set(str.nextToken())
        context.write(word,AppMaper.one)
      }

    }
  }
   class  AppReducer extends Reducer [Text,IntWritable,Text,IntWritable] {
     private val result =new IntWritable


     @throws[IOException]
     @throws[InterruptedException]
     override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
       var  sum =0
       while (values.iterator().hasNext) {
         sum += values.iterator().next().get()
       }

      result.set(sum)
      context.write(key,result)
    }
  }

  def main(args: Array[String]): Unit = {
    val conf =new Configuration()
    conf.set("","")
    val job =new Job(conf,"appAwscount")
    job.setJarByClass(classOf[ApplistCount])
    job.setMapperClass(classOf[ApplistCount.AppMaper])
    job.setCombinerClass(classOf[ApplistCount.AppReducer])
    job.setReducerClass(classOf[ApplistCount.AppReducer])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])
    FileInputFormat.addInputPath(job,new Path( args(0)))
    FileOutputFormat.setOutputPath(job,new Path(args(1)))
    System.exit(if(job.waitForCompletion(true)) 1
    else 0)


  }
}
