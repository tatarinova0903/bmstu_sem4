#include <stdio.h>
#include <time.h>

#define N 100

float scalar_vector_mult_c(float a[], float b[], int n)
{
    float res = 0;
    for(int i = 0; i < n; i++)
    {
        res += a[i] * b[i];
    }
    return res;
}

float scalar_vector_mult_asm(float a[], float b[], int n)
{
    float res = 0;
    for (size_t i = 0; i < n; i++)
    {
        float num1 = a[i];
        float num2 = b[i];
        float temp = 0;
        __asm {
            movups xmm0, [num1]
            movups xmm1, [num2]
            mulps xmm0, xmm1
            movss [temp], xmm0
        }
        res += temp;
    }
    return res;
}



int main(void)
{
    int n = 4;
    float a[4] = { 1.0, 2.0, 31.0, 40.0 };
    float b[4] = { 40.0, 13.0, 2.0, 1.0 };
    
    clock_t start;
    float res = 0.0;
    
    start = clock();
    for (int i = 0; i < N; i++)
    {
        res = scalar_vector_mult_c(a, b, n);
    }
    printf("C res = %f\n", res);
    printf("Time = %zu\n", clock() - start);
    
    start = clock();
    for (int i = 0; i < N; i++)
    {
        res = scalar_vector_mult_asm(a, b, n);
    }
    printf("ASM res = %f\n", res);
    printf("Time = %zu\n", clock() - start);

    return 0;
}
