gcc-10 -o test.o -c test.c
nasm -f macho64 copy.asm
gcc-10 -o test.exe copy.o test.o