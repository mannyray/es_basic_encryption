#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <stdio.h>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

bool isNumber(char *str){
	while(*str){
		if(!isdigit(*str)){
			return false;
		}
		str++;
	}	
	return true;
}


//first argument: the path to word bank
//second argument: the number of files to be generated
//third argument: words per text file
int main(int argc, char* argv[]){
	int numberOfFiles,numberOfWords;
	string fileName;
	try{
		if (argc != 4){
			cout<<"Improper argument count."<<endl;
			throw 1;
		}

		try{
			fileName = argv[1];
			std::ifstream infile(fileName.c_str());
    		if(!infile.good()){
    			throw 1;
    		}
    		infile.close();
		}
		catch(...){
			cout<<"Improper file provided."<<endl;
			throw 1;
		}

		if(!(isNumber(argv[2])&&isNumber(argv[3]))){
			cout<<"Improper number of files or word count provided."<<endl;
			throw 1;
		}

		numberOfFiles = strtol(argv[2],NULL,0);
		numberOfWords = strtol(argv[3],NULL,0);

		if(!(numberOfFiles>0&&numberOfWords>0)){
			cout<<"Negative number provided"<<endl;
		}

	}
	catch(...){
		cout<<"Usage:"<<endl;
		cout<<"./a.out pathToWordBank #ofFiles #wordsPerFile"
		return -1;
	}

	ifstream wordBank;
	wordBank.open(fileName.c_str());
	vector<string> words;

	string word="";
	while(wordBank>>word){
		words.push_back(word);
		//cout<<word<<endl;
	}
	wordBank.close();

	for(int i = 0; i!= numberOfFiles; i++){
		ofstream f;
		ostringstream convert;   
		convert << i<<".txt";     
		f.open(convert.str().c_str());
		for(int k =0; k!= numberOfWords; k++){
			vector<string>::iterator ranD = words.begin();
			std::advance(ranD, rand() % words.size());
			f<<*ranD<<" ";
		}
		f.close();
		//cout<<endl<<endl;
	}


	return 0;
}