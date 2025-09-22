# SydneysGit
In the Copy File class, the initialize method creates the git repository and adds the objects folder, HEAD file, and index file into the repo, all within the folder beign worked in. 
In the tester I initialize a CopyFile object and called initialize() on it.
To test that everything is running correctly, first call the initialize method, then the verifyInit() method, and finally the cleanUp() method. After, call just the verifyInit() method and neither of the others, to confirm that the method correctly identifies if the files do not exist. The responses in the terminal should make it clear if things ran properly.
