#!/bin/bash -l
#
#SBATCH --job-name=main
#SBATCH --workdir=./
#SBATCH --export=ALL
#SBATCH --exclusive
#SBATCH --time=01:00:00

#SBATCH --partition=hodor-cpu
####SBATCH --nodes=1
####SBATCH --ntasks-per-node=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=8

#SBATCH --output=main.%j.txt
##SBATCH --mail-type=fail
##SBATCH --mail-user=[firstname].[lastname]@und.edu

echo "Job start at $(date)"
module list
echo "" 

export OMP_NUM_THREADS=1
export OMP_SCHEDULE="dynamic,1"
echo ""
echo "dynamic 1 Threads 1"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=4
export OMP_SCHEDULE="dynamic,1"
echo ""
echo "dynamic 1 Threads 4"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=8
export OMP_SCHEDULE="dynamic,1"
echo ""
echo "dynamic 1 Threads 8"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=1
export OMP_SCHEDULE="dynamic,4"
echo ""
echo "dynamic 4 Threads 1"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=4
export OMP_SCHEDULE="dynamic,4"
echo ""
echo "dynamic 4 Threads 4"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=8
export OMP_SCHEDULE="dynamic,4"
echo ""
echo "dynamic 4 Threads 8"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=1
export OMP_SCHEDULE="dynamic,8"
echo ""
echo "dynamic 8 Threads 1"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=4
export OMP_SCHEDULE="dynamic,8"
echo ""
echo "dynamic 8 Threads 4"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

export OMP_NUM_THREADS=8
export OMP_SCHEDULE="dynamic,8"
echo ""
echo "dynamic 8 Threads 8"
echo ""

srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 
srun ./main 2000 

echo "Job end at $(date)"
