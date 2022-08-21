@echo on
del main.exe
MASM.EXE /ZI main.asm
MASM.EXE /ZI input.asm
MASM.EXE /ZI output.asm
MASM.EXE /ZI transl.asm
link.exe main.obj input.obj output.obj transl.obj
del *.obj
