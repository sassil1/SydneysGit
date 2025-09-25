# SydneysGit
In the Copy File class, the initializerepo method creates the git repository and adds the objects folder, HEAD file, and index file into the repo, all within the folder beign worked in. 
The genSha1 method takes in a file and returns sha1 hash based on the file contents in a string
The storeFileObj method takes in a file and inputs its sha1 hash into objects as a file with og file contents inside 
The storeFileInd method inputs the sha1 along with the original file name into the index file

In the tester I initialize a CopyFile object, and a random file called output.txt. I call initializerepo(), then run verifyInit which is a method in the tester that confirms through printed comments that the initialization worked. Then I make sure my genSha1 method works alone, then run the storeFileObj/Ind methods. Then I delete the output.txt files and the entire git folder with a method called cleanUp that is also written in the tester.

To test that everything is running correctly, follow the comments on the tester. The terminal messages should make it clear if things ran properly
