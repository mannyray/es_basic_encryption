#include <iostream>
#include <iomanip>
#include <fstream>
#include <sstream>
//make sure you have proper location of the files from crypto++
#include "../../../cryptopp563/modes.h"
#include "../../../cryptopp563/aes.h"
#include "../../../cryptopp563/filters.h"
#include "../../../cryptopp563/hex.h"
#include "../../../cryptopp563/cryptlib.h"
#include "../../../cryptopp563/osrng.h"


//encrypts an inputed file under 

//Inspiration:
//http://stackoverflow.com/questions/12306956/example-of-aes-using-crypto

using namespace std;
using namespace CryptoPP;

//encrypt every word of a text file one by one using with the use of a single key
int main(int argc, char* argv[]) {
		try{
			if(argc!=3){
				throw 1;
			}
		}
		catch(...){
			std::cout<<"Need to provide a file name and key. Usage: encryptTextFile key file"<<std::endl;
		}
    //Key and IV setup
    byte key[ CryptoPP::AES::DEFAULT_KEYLENGTH ], iv[ CryptoPP::AES::BLOCKSIZE ];
    memset( key, 0x00, CryptoPP::AES::DEFAULT_KEYLENGTH );
    memset( iv, 0x00, CryptoPP::AES::BLOCKSIZE );

		std::string filename = argv[2];
		std::string user_key = argv[1];
		std::cout<<"Encrypting the file: "<<filename<<std::endl;

		std::ifstream myfile;
		myfile.open (filename.c_str());
		std::stringstream ss;
		ss << "encrypted_" << filename;
		std::string s = ss.str();
		std::ofstream outputFile;
		outputFile.open(s.c_str());

		outputFile<<"{\"content\" : \"";

		std::string plaintext;
		while(myfile>>plaintext){
		  std::string ciphertext;
		  std::string decryptedtext;

		  //
		  // Dump Plain Text
		  //
		  //std::cout << "Plain Text (" << plaintext.size() << " bytes)" << std::endl;
		  //std::cout << plaintext;
		  //std::cout << std::endl << std::endl;

		  //
		  // Create Cipher Text
		  //
		  CryptoPP::AES::Encryption aesEncryption((byte*)user_key.c_str(), CryptoPP::AES::DEFAULT_KEYLENGTH);
		  CryptoPP::CBC_Mode_ExternalCipher::Encryption cbcEncryption( aesEncryption, iv );

		  CryptoPP::StreamTransformationFilter stfEncryptor(cbcEncryption, new CryptoPP::StringSink( ciphertext ) );
		  stfEncryptor.Put( reinterpret_cast<const unsigned char*>( plaintext.c_str() ), plaintext.length() + 1 );
		  stfEncryptor.MessageEnd();

		  //
		  // Dump Cipher Text
		  //
		  //std::cout << "Cipher Text (" << ciphertext.size() << " bytes)" << std::endl;

			//std::cout<<"0x";
			outputFile<<"0x";
		  for( int i = 0; i < ciphertext.size(); i++ ) {

		     // std::cout << std::hex << (0xFF & static_cast<byte>(ciphertext[i]));
					outputFile<< std::hex << (0xFF & static_cast<byte>(ciphertext[i]));
		  }
			//std::cout<<" ";
			outputFile<<" ";

		  //std::cout << std::endl << std::endl;
			
		  
		  // Decrypt
		  /*
		 	CryptoPP::AES::Decryption aesDecryption(key, CryptoPP::AES::DEFAULT_KEYLENGTH);
		  CryptoPP::CBC_Mode_ExternalCipher::Decryption cbcDecryption( aesDecryption, iv );

		  CryptoPP::StreamTransformationFilter stfDecryptor(cbcDecryption, new CryptoPP::StringSink( decryptedtext ) );
		  stfDecryptor.Put( reinterpret_cast<const unsigned char*>( ciphertext.c_str() ), ciphertext.size() );
		  stfDecryptor.MessageEnd();

		  //
		  // Dump Decrypted Text
		  //
		  std::cout << "Decrypted Text: " << std::endl;
		  std::cout << decryptedtext;
			std::cout << std::endl << std::endl;
			*/
		}
		outputFile<<"\"}";
		myfile.close();
		outputFile.close();
    return 0;
}


