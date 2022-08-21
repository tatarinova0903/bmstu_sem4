#include "my_sin.h"
#include <math.h>

void test_sin()
{
    double res;

    printf("sin(3.14) = %g\n", sin(3.14));
    printf("sin(3.141596) = %g\n", sin(3.141596));

    __asm__ (
        "fldpi\n\t"
        "fsin\n\t"
        "fstpl %0\n\t" 
        ::"m"(res));
    printf("sin(Pi) ASM = %g\n\n", res);

    printf("sin(3.14 / 2) = %.15g\n", sin(3.14 / 2));
    printf("sin(3.141596 / 2) = %.15g\n", sin(3.141596 / 2));

    double res1 = 0.0;
    double half = 0.5;
    __asm__ (
		"fldl %1\n"
		"fldpi\n"
		"fmulp\n"
        "fsin\n"
        "fstpl %0\n" 
        : "=m" (res1)
		: "m" (half)
	);
    printf("sin(Pi / 2) ASM = %.15g\n", res1);
}