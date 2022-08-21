#include <stdio.h>
#include <string.h>

#define N 100

extern int copy(char *dest, char *src, int len);

int my_strlen(char *string) {
    int len = 0;

    __asm__ (
        "xor %%al, %%al\n\t"
        "mov %1, %%rdi\n\t"
        "mov $0xffffffff, %%ecx\n\t" // -1
        "repne scasb\n\t"
        "not %%ecx\n\t"
        "dec %%ecx\n\t" 
        "mov %%ecx, %0"
        : "=r"(len) : "r"(string) : "%ecx", "%rdi", "%al"
    );

    return len;
}

int main(void)
{
    setbuf(stdout, NULL);

    char string[] = "abracadabra";

    // printf("Input string: ");
    // scanf("%s", string);

    int len = my_strlen(string);
    
    printf("ASM length: %d\nC length: %d\n", len, strlen(string));

    char *string_copy = string + 2;

    copy(string_copy, string, len - 2);
    printf("ORIGINAL: %s\nCOPY: %s\n", string, string_copy);

    return 0;
}