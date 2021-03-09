# nobug

Our work is in hope of seaching and killing a bug.

## 软工三——基于IR模型的bug定位
迭代一：\
使用最常见的IR模型VSM进行索引和建模，计算已经预处理好的缺陷报告（在report_preprocessed文件夹下）和每个源代码文件（在class_preprocessed文件夹下）之间的相似程度，然后按照相似度得分高低对这些源代码文件进行降序排序，并输出相似度得分
1. 任务总结：VSM模型（vector space model），相似度计算，文件降序排序（源代码）
2. 任务规划  
Step1. 预处理。对源文件(.xml)预处理（源文件->一串词组）  
Step2. 构建索引。构建源文件的索引，便于模型查找源文件。---------迭代一\
Step3. 抽取特征。抽取bug文件(.xml)的特征。\
Step4. 预处理。对bug文件(.xml)预处理（文件->一串词组，同1）。----------迭代一\
Step5. 查找排序。利用模型，计算相似度，排序。-------迭代一

