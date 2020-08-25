#!/bin/bash -l
#
#SBATCH --job-name=pi
#SBATCH --workdir=./
#SBATCH --export=ALL
#SBATCH --exclusive
#SBATCH --time=00:01:00

#SBATCH --partition=hodor-cpu
####SBATCH --nodes=1
####SBATCH --ntasks-per-node=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=8

#SBATCH --output=pi.%j.txt
##SBATCH --mail-type=fail
##SBATCH --mail-user=[firstname].[lastname]@und.edu

echo "Job start at $(date)"
module list
echo ""

echo "./pi 10000000"
srun ./pi 10000000

export OMP_NUM_THREADS=$SLURM_CPUS_PER_TASK
echo ""
echo "Number of OpenMP Threads: $OMP_NUM_THREADS"
echo ""
echo "./pisync 10000000"
srun ./pisync 10000000

echo ""
echo "./piredux1 10000000"
srun ./piredux1 10000000

echo ""
echo "./piredux2 10000000"
srun ./piredux2 10000000

echo ""
echo "./piredux3 10000000"
srun ./piredux3 10000000

echo ""
echo "./pireduxbadd 10000000"
srun ./pireduxbad 10000000

echo "Job end at $(date)"

