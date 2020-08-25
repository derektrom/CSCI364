#include <iostream>
#include <omp.h>

int main(int argc, char *argv[]) {
    using namespace std;

    if (argc != 2) {
        cout << "Usage: $ " << argv[0] << " <STEPS>" << std::endl;
        exit(1);
    }

    long num_steps = std::stoi(argv[1]);
    double step = 1.0 / (double)num_steps;

    double end_time;
    double start_time = omp_get_wtime();

    double sum = 0.0;

// why is this broken?    
#pragma omp parallel reduction (+:sum)
    {
        double x;
        for (int i = 0; i < num_steps; i++) {
            x = (i + 0.5) * step;
            sum += 4.0 / (1.0 + x * x);
        }
    }

    double pi = step * sum;

    end_time = omp_get_wtime();

    cout << "pi: " << pi << endl;
    cout << "time: " << end_time - start_time << " ms" << endl;
}
