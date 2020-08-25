#!/bin/bash -l
# 
#SBATCH --job-name=hw5
#SBATCH --workdir=./
#SBATCH --export=ALL
#SBATCH --exclusive
#
#SBATCH --time=00:05:00
#SBATCH --partition=hodor-gpu
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#
#SBATCH --output=hw5.%j.txt
##SBATCH --mail-type=fail
##SBATCH --mail-user=[firstname].[lastname]@und.edu

module list
echo ""
echo "add..."
srun nvprof ./hw5 1000000 256

echo ""
echo "error 1"
srun nvprof ./hw5

echo ""
echo "error 2"
srun nvprof ./hw5 0 0
