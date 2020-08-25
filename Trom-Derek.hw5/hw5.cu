/*
 *Derek Trom
 *HW5 CSCI364
*/
#include <stdlib.h>
#include <iostream>
#include <math.h>
#include <iomanip>
#include <cstdio>


__device__ float add(float num){

    float outnum = num + 1;
    return outnum;   
    
}

__global__
void func1(float *xd, float *yd, int n) {
    int threadId = blockIdx.x * blockDim.x + threadIdx.x;
    int stride = blockDim.x * gridDim.x;
    for (int i = threadId; i < n; i+= stride) {
        yd[i] = add(xd[i]);
    }
}
__global__ 
void createArrays(float *in, float *out, int n){
    int threadId = blockIdx.x * blockDim.x + threadIdx.x;
    int stride = blockDim.x * gridDim.x;
    for (int i = threadId; i < n; i+=stride) {
        in[i] = 1.0f;
        out[i] = 0.0f;
    }
    
}
int main(int argc, char **argv){
    using namespace std;
    if( argc< 3){
        cerr<<"Usage: "<<argv[0]<<" <length of arrays> <num threads/block>"<<endl;
        return 1;
    }
    int threads_per_block = atoi(argv[2]);    
    int sizeOfArray = atoi(argv[1]);
    if (sizeOfArray < 1 or threads_per_block < 1){
        cerr<<"Array length and block size must be > 0"<<endl;
        return 1;
    }
    float *xd, *yd;
    cudaMallocManaged(&xd, sizeOfArray*sizeof(float));
    cudaMallocManaged(&yd, sizeOfArray*sizeof(float));
    //---------PHASE ONE----------//
    int numBlocks = (sizeOfArray + threads_per_block- 1) / threads_per_block;
    createArrays<<<numBlocks, threads_per_block>>>(xd, yd, sizeOfArray);
    
    func1<<<numBlocks, threads_per_block>>>(xd,yd,sizeOfArray);
    cudaDeviceSynchronize();
    float maxError = 0.0f;
    for (int i = 0; i < sizeOfArray; i++)
    {
        maxError = fmax(maxError, fabs(yd[i]-2.0f));
    }
    cout<<"Phase 1"<<endl;
    cout<<endl<<"Array size: "<<sizeOfArray<<endl;
    cout<<"Threads per block: "<<threads_per_block<<endl;
    cout<<"Number of blocks: "<<numBlocks<<endl;
    cout << "Max error: " << maxError << endl;   
    
    //--------Phase 2-------//
    //Use half the number of blocks to get the next number but use
    //the same kernel function
    threads_per_block = threads_per_block/2;
    createArrays<<<numBlocks, threads_per_block>>>(xd, yd, sizeOfArray);
    func1<<<numBlocks, threads_per_block>>>(xd,yd,sizeOfArray);
    cudaDeviceSynchronize();
    for (int i = 0; i < sizeOfArray; i++)
    {
        maxError = fmax(maxError, fabs(yd[i]-2.0f));
    }
    cout<<"Phase 2"<<endl;
    cout<<endl<<"Array size: "<<sizeOfArray<<endl;
    cout<<"Threads per block: "<<threads_per_block<<endl;
    cout<<"Number of blocks: "<<numBlocks<<endl;
    cout << "Max error: " << maxError << endl;   
    
    
    cudaFree(xd);
    cudaFree(yd);
    return 0;
}
