/*
 * Derek Trom
 * CSCI364HW4
 *openMP 
*/
#include <iostream>
#include <random>
#include <sys/time.h>
#include <omp.h>
#include <sstream>
#include <fstream>
#ifdef TEST
#define random 0
#else
#define random 1
#endif
#define CHUNK_1 1
#define CHUNK_4 4
#define CHUNK_8 8


void printArray(int **pInt, int size) {
    int i,j;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            std::cout << pInt[i][j] << " ";
        }
        std::cout << "\n";
    }
}


void allocate(int*** a, int*** b, int*** c, int rows, int cols){

     // allocation of 3 arrays
     *a = new int* [rows];
     *b = new int* [rows];
     *c = new int* [rows];
             // for each row
     for (int i = 0; i < rows; i++){
         (*a)[i] = new int[cols];
         (*b)[i] = new int[cols];
         (*c)[i] = new int[cols];
     }
}

void convert(int** matrixA, int** matrixB,int **flatA, int **flatB, int dimension){
	for(int i=0; i<dimension; i++){
		
                for(int j=0; j<dimension; j++){
			flatA[i * dimension + j] = &matrixA[i][j];
			flatB[j * dimension + i] = &matrixB[i][j];
		}
	}
}
void initializeTest(int** a, int** b, int** c, int rows, int cols){
    //set for testing 
    for (int i = 0; i < rows; i++){
        for (int j = 0; j < cols; j++){
            a[i][j] = 1;
            b[i][j] = 1;
            c[i][j] = 0;
        }
    } 
}


void initializeRandom(int** a, int** b, int** c, int rows, int cols){
    //random filler for arrays
    const int seed = 1;
    std::mt19937 engine(seed);
    std::uniform_int_distribution<int> dist(0, 100);
    for (int i = 0; i < rows; i++){
        for (int j = 0; j < cols; j++){
            //set reandoms for two other matrices
            a[i][j] = dist(engine);
            b[i][j] = dist(engine);
            c[i][j] = 0; //set result matrix to 0
        }
    }
}
void deleteArrays(int*** a, int*** b, int*** c, int rows, int cols){
    // delete pointers
    for (int i = 0; i < rows; i++){
        delete[](*a)[i];
        delete[](*b)[i];
        delete[](*c)[i];
    }
    // delete arrays
    delete[] * a;
    delete[] * b;
    delete[] * c;
}

int main(int argc, char **argv) {
    void printArray(int **pInt, int size);
    int  rows, columns, print;
    if (argc == 1){
        std::cerr << "Usage: " << argv[0] << " <matrix size> [<print flag>]" << std::endl;
        return 1;
    }
    else if (argc == 2){
        print = 0;
    }
    else{
        char *yes = argv[2];
        if (*yes == 'y' or *yes == 'Y' or *yes == '1' ){
           print = 1;
        }
        else{
            std::cerr << "Usage: " << argv[0] << " <matrix size> [<Y|y|1>]" << std::endl;
            return 1;
        }
    }
    char *size = argv[1];
    rows = atoi(size);
    columns = atoi(size);

    // dynamically allocate space for each matrix a,b,c
    int *flatA[2000*2000];
    int *flatB[2000*2000];
    int** a = 0;
    int** b = 0;
    int** mult = 0;
    std::ofstream outfile;

        allocate(&a, &b, &mult, rows, columns);
        if(random){
            initializeRandom(a,b,mult,rows, columns);    
        }
        else{
            initializeTest(a,b,mult, rows, columns);
        }
        int i, j, k;
        int tot;         
        //convert(a, b,flatA,flatB, rows);
        double start, end, elapsed; 
        start = omp_get_wtime();
	
        #pragma omp parallel for private(i,j,k) shared(a,b,mult)  

        for (i = 0; i < rows; i++){    
            for (j = 0; j < rows; j++){   
                mult[i][j] = 0;

                for (k = 0; k < rows; k++){
                    mult[i][j] += a[i][k] * b[k][j];
                }
            }
        }
  	      
        end =  omp_get_wtime();
        elapsed = (end - start);
    //for printing result
        if (print == 1){
            printArray(mult, rows);
        }
        
        std::cout<< "Elapsed time: "<<elapsed<< std::endl;
        deleteArrays(&a,&b,&mult, rows, columns);
        outfile << "Elapsed time: "<<elapsed<<std::endl;
   
    
   return 0;
}
    
