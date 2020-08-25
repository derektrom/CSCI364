//
// Created by Derek Trom on 4/24/20.
//
#include <iostream>
#include <cstdlib>
#include <cstring>
#include <random>
#include <omp.h>

using namespace std;

// functions

// allocate arays
void allocate_arrays(int*** a, int*** b, int*** c, int rows, int cols)
{

// an array of pointers to separate arrays of integers
    *a = new int* [rows];
    *b = new int* [rows];
    *c = new int* [rows];

// for each row
    for (int i = 0; i < rows; i++)
    {
        (*a)[i] = new int[cols];
        (*b)[i] = new int[cols];
        (*c)[i] = new int[cols];
    }

}

// initalize array with ones
void initialize_arrays_ones(int** a, int** b, int** c, int rows, int cols)
{
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            a[i][j] = 1;
            b[i][j] = 1;
            c[i][j] = 0; // clear result
        }
    }
}

// initalize array with ranom values
void initialize_arrays_random(int** a, int** b, int** c, int rows, int cols)
{
// initialize engine and distribution
    const int seed = 1;
    std::mt19937 engine(seed);
    std::uniform_int_distribution<int> dist(0, 100);

// initialize with random numbers

    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            a[i][j] = dist(engine);
            b[i][j] = dist(engine);
            c[i][j] = 0; // clear result
        }
    }
}

// initalize array with ones
void initialize_arrays(int** a, int** b, int** c, int rows, int cols)
{
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            a[i][j] = 1;
            b[i][j] = 1;
            c[i][j] = 0; // clear result
        }
    }
}

// print matrix
void print_matrix(int** c, int rows, int cols)
{

    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            cout << c[i][j];

            if (j < cols - 1) cout << " ";
        }

        cout << endl;

    }
}


// dealocate memory
void deallocate_arrays(int*** a, int*** b, int*** c, int rows, int cols)
{
// delete an array of pointers to separate arrays of integers
    for (int i = 0; i < rows; i++)
    {
        delete[](*a)[i];
        delete[](*b)[i];
        delete[](*c)[i];
    }

// delete a contiguous array of integer
    delete[] * a;
    delete[] * b;
    delete[] * c;

}

int main(int argc, char* argv[])
{
    int rows = 0; // size of matrix
    int cols = 0;
    bool print = false;

// check for matrix size
    if (argc >= 2)
    {
        rows = cols = atoi(argv[1]);

// check N
        if (rows <= 0 || cols <= 0)
        {
            cout << "matrix size too small" << endl;
            return 0;
        }

// check for print flag
        if (argc >= 3)
        {
            if (strcmp(argv[2], "Y") == 0 || strcmp(argv[2], "y") == 1 || strcmp(argv[2], "1") == 0)
            {
                print = true;
            }
        }

    }

// bad command line
    else
    {
        cout << "matrix size and print flag 'Y', 'y' or '1' expected" << endl;
        cout << "run like this: $ ./mm <matrix size> [<print flag>] " << endl;
        return 0;
    }



    omp_set_num_threads(omp_get_num_procs());

// dynamically allocate space for each matrix a,b,c

    int** a = 0;
    int** b = 0;
    int** c = 0;

    allocate_arrays(&a, &b, &c, rows, cols);


// initialize arrays

// test mode
#ifdef TEST

    // intialize arrays with ones
initialize_arrays_ones(a, b, c, rows, cols);

#else

// initialize with random numbers

    initialize_arrays_random(a, b, c, rows, cols);

# endif

    double start = 0, end = 0;

    int i=0, j=0, k=0;

// do multiplication

    start = omp_get_wtime();

#pragma omp parallel for private(i,j,k) shared(a,b,c)

    for (i = 0; i < rows; i++)
    {
        for (j = 0; j < cols; j++)
        {
            c[i][j] = 0;

            for (k = 0; k < rows; k++)
            {
                c[i][j] += a[i][k] * b[k][j];
            }
        }
    }


    end = omp_get_wtime();

// print matrix
    if (print)
    {

        print_matrix(c, rows, cols);
    }

// print out time for multiplication
    double elapsed = (end - start);
    cout << "elapse time  " << elapsed  << " seconds" << endl;

// Deallocate matrix memory
    deallocate_arrays(&a, &b, &c, rows, cols);

    return 0;

}

