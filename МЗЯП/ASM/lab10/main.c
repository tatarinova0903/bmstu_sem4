#include <float.h>
#include <stdio.h>
#include <time.h>

#define N 1

float c_scalar_prod(const float *a, const float *b, int n) {
    float res = 0;
    for (size_t i = 0; i < n; i++)
    {
        res += a[i] * b[i];
    }
    return res;
}

float sse_scalar_prod(float src_a[], float src_b[], int n)
{
    float tmp = 0;
    float vec_a[3] = {1, 2, 3};
    float vec_b[3] = {1, 2, 3};
    float *a = vec_a;
    float *b = vec_b;
    for (size_t i = 0; i < n; i++, a++, b++)
    {
        tmp = 0;
        *a = 1;
        *b = 2;
        printf("%f %f\n", *a, *b);
        __asm__
        (
         "movaps %%xmm0, %1\n\t"
         "movaps %%xmm1, %2\n\t"
         "mulps %%xmm0, %%xmm1\n\t"
         "movhlps %%xmm1, %%xmm0\n\t"
         "addps %%xmm0, %%xmm1\n\t"
         "movaps %%xmm1, %%xmm0\n\t"
         "shufps %%xmm0, %%xmm0, $0x1\n\t"
         "addps %%xmm0, %%xmm1\n\t"
         "movss %0, %%xmm0\n"
         : "=m"(tmp)
         : "m"(*a), "m"(*b)
         : "xmm0", "xmm1"
         );
    }
    return tmp;
}

int main() {
    float res_c = 0;
    
    int n = 16;
    float vec_a[16] = {1, 2, 3};
    float vec_b[16] = {1, 2, 3};
    
    clock_t start = clock();
    printf("TEST C IMPLEMENTATION \n");
    for (size_t i = 0; i < N; i++) {
        res_c = c_scalar_prod(vec_a, vec_b, n);
    }
    clock_t time_c = clock() - start;
    
    printf("C scalar multiply = %zu\n", time_c);
    printf("\nTEST SSE ASSEMBLY\n");
    float res_asm = 0;
    start = clock();
    for (size_t i = 0; i < N; i++) {
        sse_scalar_prod(vec_a, vec_b, n);
    }
    clock_t time_asm = clock() - start;
    printf("ASM scalar multiply = %zu\n", time_asm);
    printf("\nLAST VALUES ARE EQUALS %d\n", res_asm == res_c);
    
    return 0;
}

