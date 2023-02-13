# nobug

Our work is in hope of seaching and killing any bug.

## 软工三——基于IR模型的bug定位
### 迭代一
使用最常见的IR模型VSM进行索引和建模，计算已经预处理好的缺陷报告（在report_preprocessed文件夹下）和每个源代码文件（在class_preprocessed文件夹下）之间的相似程度，然后按照相似度得分高低对这些源代码文件进行降序排序，并输出相似度得分
1. 任务总结：VSM模型（vector space model），相似度计算，文件降序排序（源代码）
2. 任务规划  
Step1. 预处理。对源文件(.xml)预处理（源文件->一串词组）  
Step2. 构建索引。构建源文件的索引，便于模型查找源文件。---------迭代一\
Step3. 抽取特征。抽取bug文件(.xml)的特征。属于优化\
Step4. 预处理。对bug文件(.xml)预处理（文件->一串词组，同1）。----------迭代一\
Step5. 查找排序。利用模型，计算相似度，排序。-------迭代一
3. 待解决的问题  
(1). 哪些预处理的方式？  
(2). 哪些特征？  
(3). 选择什么作为相似度？  
(4). 哪种模型？
4. 迭代一 todo.list  
Step1. 源文件的预处理 (Done. in data/class_preprocessed)  
Step2. 构建索引。根据关键词，能找到对应文件。(Todo1 怎么构建这样的缩影)   
Step3. 优化暂不做  
Step4. 查询的缺陷文件的预处理。(Done. in data/report_preprocessed)  
Step5. 查找排序。(Todo2 VSM模型代码、计算相似度、排序)

构建索引：https://blog.csdn.net/qq_35975685/article/details/79811859
https://blog.csdn.net/m0_37913549/article/details/78989078

### 迭代二
在迭代一基础上，增加了以下部分：
1. 预处理。补足迭代一缺失的Step1部分。去关键词、去停用词、词性变化、复合词拆分。
2. 增加更多模型。如：曼哈顿距离、欧式距离等。
3. 增加评价排行指标。有：Top@K、MRR、MAP等。
4. 增加"相似文件"这一特性进行性能优化，补足迭代一缺失的Step3部分。
5. 将本地数据库放在云端，任意ip可以访问。不再是本地单机项目。
6. 初步实现前端：排行榜界面。

### 迭代三
选择方向：改进查询
