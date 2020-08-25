#include <iostream>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        std::cout << "Usage: " << argv[0] << " <STEPS>" << std::endl;
        exit(1);
    }
    long num_steps = std::stoi(argv[1]);

    double x, pi, sum = 0.0;

    double step = 1.0 / (double) num_steps;

    for (int i = 0; i < num_steps; i++) {
        x = (i + 0.5) * step;
        sum = sum + 4.0 / (1.0 + x * x);
    }
    pi = step * sum;
    std::cout << "pi: " << pi << std::endl;
}

