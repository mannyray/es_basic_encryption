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

//encrypt a word under a specific key
int main(int argc, char* argv[]) {
	try{
		if(argc!=3){
			throw 1;
		}
	}
	catch(...){
		std::cout<<"Need to provide a key and a word to encrypt. Usage: ./encrypt key word"<<std::endl;
	}

	//Key and IV setup
	byte key[ CryptoPP::AES::DEFAULT_KEYLENGTH ], iv[ CryptoPP::AES::BLOCKSIZE ];
	memset( key, 0x00, CryptoPP::AES::DEFAULT_KEYLENGTH );
	memset( iv, 0x00, CryptoPP::AES::BLOCKSIZE );

	std::string user_key = argv[1];
	std::string plaintext = argv[2];

	std::string ciphertext;
	std::string decryptedtext;

	CryptoPP::AES::Encryption aesEncryption((byte*)user_key.c_str(), CryptoPP::AES::DEFAULT_KEYLENGTH);
	CryptoPP::CBC_Mode_ExternalCipher::Encryption cbcEncryption( aesEncryption, iv );

	CryptoPP::StreamTransformationFilter stfEncryptor(cbcEncryption, new CryptoPP::StringSink( ciphertext ) );
	stfEncryptor.Put( reinterpret_cast<const unsigned char*>( plaintext.c_str() ), plaintext.length() + 1 );
	stfEncryptor.MessageEnd();

	//
	// Dump Cipher Text
	//
	//std::cout << "Cipher Text (" << ciphertext.size() << " bytes)" << std::endl;

	std::cout<<"0x";

	for( int i = 0; i < ciphertext.size(); i++ ) {

		std::cout << std::hex << (0xFF & static_cast<byte>(ciphertext[i]));

	}
	std::cout<<endl;


  return 0;
}


