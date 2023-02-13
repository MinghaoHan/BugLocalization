# BugLocalization
<iframe src="//player.bilibili.com/player.html?aid=845164781&bvid=BV1S54y1L7Gz&cid=325693901&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe>   
# Team: nobug

Our work is in hope of seaching and killing any bug.

## Software Engineering and Computing III - Java Bug Location Based on Information Retrieval
### iteration 1
Use the most common IR model VSM for indexing and modeling, calculate the similarity between the preprocessed defect report (under the report_preprocessed folder) and each source code file (under the class_preprocessed folder), and then follow the similarity Sort these source code files in descending order according to the degree score, and output the similarity score
1. Task: implement VSM model(vector space model), calculate similarity, recommendation list
2. Plan  
Step1. Preprocess(source code). preprocess source code file(.xml)(source file -> a sequence of key words)  
Step2. Indexing. Index for source code file for fast query in library. ---------iteration 1\
Step3. Extract features. Extract bug features from bug description file (.xml). ---------Optimazation\
Step4. Preprocess(bug). preprocess bug file(.xml)(bug file -> a sequence of key words). ----------iteration 1\
Step5. Search and Sort. Use some models to calculate similarity and sort. -------Iteration 1
3. TO think  
(1). How to preprocess?  
(2). What features to extract？  
(3). The metrics to calculate similarity?  
(4). What models to choose?
4. Iteration 1 todo.list  
Step1. preprocess source code (Done. in data/class_preprocessed)  
Step2. indexing. Can find related file by querying key words (Todo1: How to build such map)   
Step3. No optimization needed.  
Step4. preprocess bug description. (Done. in data/report_preprocessed)  
Step5. Query.(Todo2: VSM model, similarity, sorting)

Indexing: https://blog.csdn.net/qq_35975685/article/details/79811859
https://blog.csdn.net/m0_37913549/article/details/78989078

### Iteration 2
Based on Iteratin1, add functions as follow：
1. Further preprocess. Remove keywords of java, remove stop words, change parts of speech, and split compound words.
2. Compose more models. Such as, Manhattan distance, Euclidean distance, etc..
3. Add more evaluation metrics. Such as, Top@K, MRR, MAP.
4. Add optimization. Calculate similar files to query more fast without extra calculation.
5. Put off-line database on the cloud server which can be accessed from Internet with key.
6. A Vue interface.

### Iteration 3
Query through history.
