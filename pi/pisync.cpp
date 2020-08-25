#include <iostream>
#include <omp.h>
#include <sstream>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        std::cout << "Usage: $ ./a.out <STEPS> " << std::endl;
        exit(1);
    }
    long num_steps = std::stoi(argv[1]);

    double end_time;
    double start_time = omp_get_wtime();

    double pi = 0.0;
    double step = 1.0 / (double)num_steps; // width of rectangle

#pragma omp parallel 
    {
        double x;
        double sum;
        int id = omp_get_thread_num();
        int nthrds = omp_get_num_threads();
        for (int i = id; i < num_steps; i=i+nthrds) {
            x = (i + 0.5) * step;
            sum += 4.0 / (1.0 + (x * x));
        }
#pragma omp critical
        pi += step * sum;
    }
    

    end_time = omp_get_wtime();
    std::cout << "pi: " << pi << std::endl;
    std::cout << "elapsed time: " << end_time - start_time << std::endl;
}   

