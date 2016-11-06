# File Upload Assignment

The sample app is implemented using Spring boots, Gradle, React and an in memory db(hashmap).
The files are being uploaded to a directory named `uploaded-files-storage` under the server running folder.

The front-end code is under `web` directory

## Build

From the command line or terminal:

* Frontend: `<extracted folder>/web$ npm install & npm run build`
* Backend `<extracted folder>/$ gradle build`
* To run the application `java -jar build/libs/upload-0.1.jar`
Then you can point your browser to `http://localhost:8080/` to use the application.


## Design Considerations

This is a very simple implementation of uploading file with some metadata with the ability to view
or download the files. There is no validation on the input file or metadata so for the future there
should be validation for manadatory fields, file size and allowed file types.

There is no tests so for the future test cases needs to be added for the parts that has logic handling
and the frontend. The exception handling is very simple for now, we could have more error status codes and more descriptive
messages. The css is just basic, more styling should be added later on.

This was my first react and gradle application so if you see something very off please send feedbacks.
For sure more text, documentation, validations and refactoring could be done but cause of the
time constraints on my side this is a simple draft.



