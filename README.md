# vs-Beleg-ATEG
Belegarbeit für Verteilte Systeme von: Ahmad Ali Nabizada, Tobias Kroll, Eric Hübel, Georg Richter

UML-Diagramm:

+---------------------+          +---------------------+
| <<interface>>       |          |     Controller      |
|  UserInterface      |<-------->|---------------------|
|---------------------|          | +startComputation() |
| +displayImage()     |          | +divideTasks()      |
| +showProgress()     |          | +collectResults()   |
+---------------------+          +---------------------+
                                          |
                                          | creates / controls
                                          v
                                +---------------------+
                                |       Task          |
                                |---------------------|
                                | +startX             |
                                | +startY             |
                                | +width              |
                                | +height             |
                                +---------------------+
                                          |
                                          | used by
                                          v
                                +---------------------+
                                |  MandelbrotEngine   |
                                |---------------------|
                                | +computePixel(x,y)  |
                                | +computeTile(Task)  |
                                +---------------------+
                                          ^
                                          |
                        calls             |      
+---------------------+    executes       |
|     Worker          |<------------------+
|---------------------|                  
| +run()              |                  
| +getTask()          |                  
+---------------------+                  
        ^                               
        | executes tasks from           
+---------------------+                 
|     ThreadPool      |                 
|---------------------|                 
| +submit(Task)       |                 
| +shutdown()         |                 
+---------------------+                 
        |                                 
        | outputs to                      
        v                                 
+---------------------+                  
|      Renderer        |                 
|---------------------|                 
| +drawPixel(x,y,c)   |                 
| +saveImage(file)    |                 
+---------------------+                 
