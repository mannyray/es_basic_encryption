#!/bin/bash




for line in $(find . -name "*.txt" -not -name "encrypted*.txt"); do 
	 line=${line:2}
	 echo $line
   cat $line | tr -cd '[[:alnum:]] ' > tmpFile; cat tmpFile > $line
done



for line in $(find . -name "*.txt" -not -name "encrypted*.txt"); do 
 	line=${line:2}
 	echo $line
	echo '{"content" : "'>tmpFile;
  cat  $line  >> tmpFile;
	echo '"  }'>> tmpFile;
	cat tmpFile > $line;

done

for line in $(find . -name "*.txt" -not -name "encrypted*.txt"); do 
	 line=${line:2}
	 echo $line
   cat $line | tr -cd '[[:alnum:]]{}:" ' > tmpFile; cat tmpFile > $line
done
