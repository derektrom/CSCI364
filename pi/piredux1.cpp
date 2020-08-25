#include <omp.h>
#include <iostream>

int main(int argc, char *argv[]) {
    using namespace std;

    if (argc != 2) {
        std::cout << "Usage: " << argv[0] << " <STEPS>" << std::endl;
        exit(1);
    }

    long num_steps = std::stoi(argv[1]);

    double end_time;
    double start_time = omp_get_wtime();

    double step = 1.0 / (double)num_steps;
    double sum = 0.0;


#pragma omp parallel 
    {
        double x;
#pragma omp for private(x) reduction(+:sum)
        for (int i = 0; i < num_steps; i++) {
            x = (i + 0.5) * step;
            sum += 4.0 / (1.0 + x * x);
        }
    }

    double pi = step * sum;
    end_time = omp_get_wtime();
    cout << "pi: " << pi << endl;
    cout << "elapsed time: " << end_time - start_time << endl;
    
}
