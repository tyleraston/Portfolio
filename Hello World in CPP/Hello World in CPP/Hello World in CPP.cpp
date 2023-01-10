#include <iostream>
using namespace std;

/*************************************************************
* Main is the starting point for C++ programs.
*************************************************************/
int main()
{
	// Initialize varibles
	string response;
	// Prompt user
	cout << "Hello, what is your name?";
	cin >> response;
	// Output greeting
	cout << "Hello " << response << "! My name is Tyler Aston.";
	// End
	return 0;
}