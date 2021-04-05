//ch8499fk

#include <iostream>
#include <pthread.h>
#include <errno.h>
#include <stdlib.h>

#define NSTUD 10

using namespace std;

pthread_mutex_t lock;

class Library 
{		
	public: 
		int maxCustomers;
	    int numberOfCustomers;
	    int numberOK;    
		int numberExpired;    
		int numberOfBooks;   
		Library (int);    
		void close ();    
		int findBooks (int);    
		void checkOut (int, bool, int);
		};
		
	Library::Library (int mc) 
	{
		maxCustomers = mc;
		numberOfCustomers = 0;
		numberOK = 0;
		numberExpired = 0;
		numberOfBooks = 0;
	}
	
	//closing function to create a report of all transactions taken place at Library
	void Library::close () 
	{
	cout << "Number of customers: " << numberOfCustomers << endl;
	cout << "Number of books checked out: " << numberOfBooks << endl;
	}
	
	//returns number of books student's find
	int Library::findBooks (int id) 
	{
		return ( id * id + id + 1 );
	}
	
	//
	void Library::checkOut(int id, bool expired, int books) 
	{
		pthread_mutex_lock(&lock);
		if (expired)
			numberExpired++;
		else 
		{
			numberOK++;
			numberOfBooks += books;
			cout << "student " << id << " leaves library with " << books << " books" << endl;
		}

		pthread_mutex_unlock(&lock);
	}
	
	
	
	class Student 
	{
		public:    
			int id;
			bool cardExpired;    
			Library *library;
			int books;
			Student ();    
			Student (int, bool, Library*);    
			void operator () () ;    
			int run (void *);
		};
		
		Student::Student () 
		{    
			id = 0;    
			cardExpired = false;    
			library = NULL;
		}
		
		Student::Student ( int i, bool x, Library* lib ) 
		{
			id = i;
			cardExpired = x;
			library = lib;
		}
		
		//First thread to find how many books each student finds and output what they find
		void *run (void *stud)  
		{
			pthread_mutex_lock(&lock);																		//lock the thread
			struct Student *student = (struct Student *) stud;												//create pointer to student Object from void object
			student->books = student->library->findBooks(student->id);										//student looks for books at library
			cout << "Student " << student->id << " found " << student->books << " books" << endl;			//output number of books find by student
			pthread_mutex_unlock(&lock);																	//unlock the thread
		}
		
		//second thread to checkout with the previously found books
		void *finish(void *stud)
		{
			struct Student student = *(struct Student *) stud;												//convert void object back to student object
			student.library->checkOut(student.id,student.cardExpired,student.books);						//students checkout the books
			return NULL;
		}
		
		//create variables for threads and library object for student arrays to access
		//thread once to find books then return to main
		//thread again for checkout then return to main to close
		int main(int argc, const char * argv[])
		{   
			const int NSTUDENTS = NSTUD; 
			Library* library = new Library (NSTUDENTS);															//library object that students are visiting
			pthread_t studentThreads[NSTUDENTS];																//pthread objects for threading
			Student student[NSTUDENTS];																			//array of student objects to be given to threads
			bool expired;																						//variable for if card is expired
			int error;																							//variable to check for errors
		    
		    for(int i =0; i < NSTUDENTS; i ++)																	//assign values to student objects and start threads
		    {
		    	if ( i % 2 == 0)
		    		expired = false;
		    	else
		    		expired = true;
		    	student[i].id = i;
		    	student[i].cardExpired = expired;
		    	student[i].library = library;
		    	error = pthread_create(&studentThreads[i],NULL,&run,(void *) &student[i]);
		    	if(error)
		    	{
		    		cout << "Failed to create thread" << endl;
		    		exit(i);
				}
			}
			
			cout << "All students are visiting the library.\n";											//notify all threads started
			
			for (int i = 0; i < NSTUDENTS ; i++)																//rejoin threads with outputs for books found
			{
				pthread_join(studentThreads[i],NULL);
			}
			
			cout << "All books found" << endl;																	//notify all threads rejoined
			
			for (int i = 0; i < NSTUDENTS; i ++)																//thread to checkout books previously found
			{
				library->numberOfCustomers++;
				error = pthread_create(&studentThreads[i],NULL,&finish, (void *) &student[i]);
				if(error)
				{
					cout << "Failed to create thread" << endl;
					exit(i);
 				}
			}
			
			
			for (int i = 0; i <NSTUDENTS; i ++)																	//rejoin threads with outputs for books checked out, if card is not expired
			{
					pthread_join(studentThreads[i],NULL);
			}
			
			
			
		    library->close();  																					//print closing statements for library
			
			return 0;
		}
	
	
	
	
