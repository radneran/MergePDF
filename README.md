# MergePdf
* This is a simple application for merging multiple pdfs together
* Requires Java version `1.8.0_60` or later

# Installation
* There is no installer just unzip and double-click .jar file
* In order to add the option to run from Right-Click context menu, edit the inmergepdf.reg file by replacing `%LOCATION_OF_FOLDER%` with the actual location of the extracted folder
* Make the same edit in mergepdf.bat
* unmergepdf.reg removes the option from context menu (Make sure to run it before deleting the folder and files)

# Controls
#### Add files to list
* Just drag and drop files into the window

#### Removing files from list `D`
* Select the file you want to delete and press `D`

#### Rearranging files in the list `Ctrl` 
* Select the file you want to move 
* Press and hold `Ctrl`
* Scroll to the position you want to place the file using the `UP` and `DOWN` arrow keys
* Once the destination is chosen, release the `Ctrl` key

#### Merging files `Enter`
* You can either press the **Merge** button or press `Enter`

### Note
The application saves the merged pdf in its own folder as Combined.pdf
