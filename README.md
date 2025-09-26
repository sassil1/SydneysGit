# SydneysGit
In the Copy File class, the compression instance variable decides if the code will all run with or without zip compression (true - will compress, false = won't compress). Use the setCompression method in the tester (already called in the main method) to swtich between compression/no compression

The initializerepo method creates the git repository and adds the objects folder, HEAD file, and index file into the repo, all within the folder beign worked in. 

The genSha1 method takes in a file and returns sha1 hash based on the file contents in a string

The storeFileObj method takes in a file and inputs its sha1 hash into objects as a file with og file contents inside 

The storeFileInd method inputs the sha1 along with the original file name into the index file

In the tester main method I: 
1) Initialize a CopyFile object, 
2) Call initializerepo()
3) Turn on Compression
4) Create and fill 3 files, putting one of them into a new folder
5) Run verifyInit() which is a method written in the tester that confirms that the initialization worked. 
6) Make sure my genSha1 method works alone and generates a proper Sha1 string from a file's contents
7) Run the storeFileObj/Ind methods which put all the proper files/text in the objects folder and index file 
8) Confirm that the correct sha1 hash for each file i created exists as a file name within the objects folder (isBlobInObjects(File) written in tester)
9) Delete the extra copies of the compressed zip files that exist outside of the objects folder
10) Call the cleanUp method written in the tester that clears out the text in index, deletes the files in the objects folder, and deletes the three files and one folder being used for testing - I recommend running the code with this one line commented out at first, to easily look in the explorer that everything is in the correct place, then run a second time with this line uncommented before testing again

The comments within the tester should also help guide how to use it properly