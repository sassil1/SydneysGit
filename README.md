# ME WHEN I CODE BY MYSELF:
> ## a=b+c;

# ME WHEN I CODE WITH SOMEONE WATCHING
> // Adding two variables together results in one bigger variable. <br>
> // I am choosing to call this one 'A' and it is the result of adding 'B' and 'C'! <br>
> 
> int a = 0; // initializing it here, will make it the sum later <br>
> int b = 5; // will add this, and the next thing too <br>
> int c = 5; // I already described what this is, read that comment please <br>

> // using the sum method, this is more readable and better <br>
> int tempA = sum(c, b); <br>

> if (a != tempA) // we want to make sure we don't do any unecessary operations, for big O of course. <br>
>     a = tempA; <br>

> /// @Summary <br>
> /// This method adds two variables, one called 'C' and one called 'B'. <br>
>/// @Return: int <br>
> public static int sum(int C, int B) <br>
> { <br>
>     return C + B; <br>
> }<br>


# ===============================================
#Joe'sUpdate
I made a few changes. So read this first, and if it doesn't make sense in comparison to someone else's stuff then ignore her stuff. I am too tired to read through all of it to figure out what I changed.

Basically, I changed 2 things -- the way that Sydney's code is used, and implemented a maketree thingy like we were supposed to.

Before, Sydney's code required individual method calls for each file in order to make a blob and to index it. Now I just have one method that runs and does all this automatically (unless you add the file into the gitignore).

I also implemented the maketree method:
Please read the comments.

This can all be explained and tested in my helpful Tester.java.

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
4) Create and fill 3 files
5) Run verifyInit() which is a method written in the tester that confirms that the initialization worked. 
6) Make sure my genSha1 method works alone and generates a proper Sha1 string from a file's contents
7) Run the storeFileObj/Ind methods which put all the proper files/text in the objects folder and index file 
8) Confirm that the correct sha1 hash for each file i created exists as a file name within the objects folder
9) Delete the extra copies of the compressed zip files that exist outside of the objects folder
10) Call the cleanUp method written in the tester that clears out the text in index, deletes the files in the objects folder, and deletes the three files being used for testing - I recommend running the code with this one line commented out at first, to easily look in the explorer that everything is in the correct place, then run a second time with this line uncommented before testing again

The comments on the tester should also help guide how to use it properly