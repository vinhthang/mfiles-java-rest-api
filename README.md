##M-Files REST JAVA client API
    Java lib to access M-Files REST API :), for check in one or more file.
##Features
    Able to do authentication 
    List workflows 
    List value list items 
    And finally to create MFiles Object, checkin and checkout
##References 
    http://www.m-files.com/mfws/, most java object from this
    https://github.com/jplumhoff/MFilesREST, java dynamic code
    if above link not sufficient then read MfilesClientServiceTest
    and then open your mfiles on browser, read network access for real REST api
##Not good
    the unit test is run fail, because it use my files and my authentication test, but it worked on my production
    nodejs app is better than java library, since mfiles REST is mostly json
    main service MfilesClientService will need some refactor





