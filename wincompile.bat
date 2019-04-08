:: Compiler le projet sous windows (j\'ai pas testé mais ça devrait marcher)
for /r %%a in (*.java) do ( javac -d bin -classpath bin:res:lib/miglayout15-swing.jar:lib/swingx-all-1.6.4.jar:lib/mysql-connector-java-5.1.40-bin.jar "%%a" )
