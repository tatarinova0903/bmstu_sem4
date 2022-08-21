#include <stdio.h>
#include <limits.h>
#include <time.h>
#include "numbers.h"

#define N 10000


/// MARK: - FLOAT
void sum_float(float a, float b)
{
    float res;
    for (int i = 0; i < N; i++)
        res = a + b;
}

void mult_float(float a, float b)
{
    float res;
    for (int i = 0; i < N; i++)
        res = a * b;
}

void sum_float_asm(float a, float b)
{
    float res;
    for (int i = 0; i < N; i++)
         __asm__ (
            "fldl %1\n\t"
            "fldl %2\n\t"
            "faddp\n\t"
            "fstpl %0"
            : "=m"(res)
            : "m"(a), "m"(b)
            );
}

void mult_float_asm(float a, float b)
{
    float res;
    for (int i = 0; i < N; i++)
        __asm__ (
            "flds %1\n"
            "flds %2\n"
            "fmulp\n"
            "fstps %0\n"
            : "=m"(res)
            : "m"(a), "m"(b)
            );
}


/// MARK: - DOUBLE
void sum_double(double a, double b)
{
    double res;
    for (int i = 0; i < N; i++)
        res = a + b;
}

void mult_double(double a, double b)
{
    double res;
    for (int i = 0; i < N; i++)
        res = a * b;
}

void sum_double_asm(double a, double b)
{
    double res;
    for (int i = 0; i < N; i++)
        __asm__ (
            "fldl %2\n\t"
            "fldl %1\n\t"
            "faddp\n\t"
            "fstpl %0"
            : "=m"(res)
            : "m"(a), "m"(b)
            );
}

void mult_double_asm(double a, double b)
{
    double res;
    for (int i = 0; i < N; i++)
        __asm__ (
            "fldl %2\n\t"
            "fldl %1\n\t"
            "fmulp\n\t"
            "fstpl %0"
            : "=m"(res)
            : "m"(a), "m"(b)
            );
}

/// MARK: - LONG DOUBLE
void sum_float80(__float80 a, __float80 b)
{
    __float80 res;
    for (int i = 0; i < 10000; i++)
        res = a + b;
}

void mult_float80(__float80 a, __float80 b)
{
    __float80 res;
    for (int i = 0; i < 10000; i++)
        res = a * b;
}

void sum_float80_asm(__float80 a, __float80 b)
{
    __float80 res;
     for (int i = 0; i < 10000; i++)
        __asm__(
            "fldl %2\n\t"
            "fldl %1\n\t"
            "faddp\n\t"
            "fstpl %0"
            : "=m"(res)
            : "m"(a), "m"(b)
            );
}

void mult_float80_asm(__float80 a, __float80 b)
{
    __float80 res;
    for (int i = 0; i < 10000; i++)
         __asm__(
            "fldl %1\n\t"
            "fldl %2\n\t"
            "fmulp\n\t"
            "fstpl %0"
            : "=m"(res)
            : "m"(a), "m"(b)
            );
}

/// MARK: - TESTS
void test_float()
{
    float a = 12.65, b = 48.01;
    printf("\n\n%d (FLOAT): \n", sizeof(float) * CHAR_BIT);

    int start = clock();
    sum_float(a, b);
    int end = clock();
    printf("a + b: %d\n", end - start);

    start = clock();
    mult_float(a, b);
    end = clock();
    printf("a * b: %d\n\n", end - start);  

    start = clock();
    sum_float_asm(a, b);
    end = clock();
    printf("a + b ASM: %d\n", end - start);

    start = clock();
    mult_float_asm(a, b);
    end = clock();
    printf("a * b ASM: %d\n\n\n", end - start);
}

void test_double()
{
    float a = 12.65, b = 48.01;
    printf("%d (DOUBLE): \n", sizeof(double) * CHAR_BIT);

    int start = clock();
    sum_double(a, b);
    int end = clock();
    printf("a + b: %d\n", end - start);

    start = clock();
    mult_double(a, b);
    end = clock();
    printf("a * b: %d\n\n", end - start);   


    start = clock();
    sum_double_asm(a, b);
    end = clock();
    printf("a + b ASM: %d\n", end - start);

    start = clock();
    mult_double_asm(a, b);
    end = clock();
    printf("a * b ASM: %d\n\n\n", end - start); 
}

void test_float80()
{
    __float80 a = 12.65, b = 48.01;
    printf("80 (LONG DOUBLE): \n");

    int start = clock();
    sum_float80(a, b);
    int end = clock();
    printf("a + b: %d\n", end - start);

    start = clock();
    mult_float80(a, b);
    end = clock();
    printf("a * b: %d\n\n", end - start);   

    start = clock();
    sum_float80_asm(a, b);
    end = clock();
    printf("a + b ASM: %d\n", end - start);

    start = clock();
    mult_float80_asm(a, b);
    end = clock();
    printf("a * b ASM: %d\n", end - start);  
}
