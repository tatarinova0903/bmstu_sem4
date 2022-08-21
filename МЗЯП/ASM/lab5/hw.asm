; прямоугольная цифровая матрица
; удалить строки, в которых все элементы - нечётные

STACKS SEGMENT PARA STACK 'STACK'
    DB 100 DUP (0)	
STACKS ENDS

; Сегмент данных
DATAS SEGMENT PARA 'DATA'
    ROWSMSG DB 'Input rows count: $'				; Сообщение при вводе кол-ва строк
	COLSMSG DB 'Input columns count: $'				; Сообщение при вводе кол-ва столбцов
	MTRXMSG DB 'Input matrix: $'					; Сообщение при вводе матрицы
	RESLMSG DB 'Result: $'							; Сообщение при выводе матрицы
	I DW 0										    ; Счетчик в цикле
	J DW 0											; Счетчик в цикле
	ROW_TO_DELETE DW '?'							; Ряд, который надо удалиь
	ODDCOUNT DB 0									; Количество нечетных цифр в строке
	ROWS DB 0										; Кол-ва строк
	COLS DB 0										; Кол-ва столбцов
	MAX_COLS DB 9
	MATRIX DB 9 * 9 DUP(?)							; Память для матрицы
DATAS ENDS	

; Сегмент кода
CODES SEGMENT PARA 'CODE'	
    ASSUME CS:CODES, DS:DATAS, SS:STACKS
	
; Ввода символа
INPUT_SYMBOL:
	MOV AH, 1
	INT 21h
	RET
	
; Перевод на новую строку
PRINT_NEW_STR:
	MOV AH, 2
	MOV DL, 13
	INT 21h
	MOV DL, 10
	INT 21h
	RET

; Печать пробела
PRINT_SPACE:
	MOV AH, 2
	MOV DL, ' '
	INT 21h
	RET
	
; Вывод символа
OUTPUT_SYMBOL:
	MOV AH, 2
	INT 21h
	RET

; Вывод сообщения
OUTPUT_MESSAGE:
	MOV AH, 9
	INT 21h
	RET

; Удаление строки 
DELETE_ROW:
	MOV J, 0
	MOV AL, ROWS
	SUB AX, ROW_TO_DELETE
	DEC AL
	MOV CX, AX
	CMP CX, 0
	JE DELETE_EXIT
	CHANGE:
		MOV AX, ROW_TO_DELETE
		MUL MAX_COLS
		MOV SI, AX
		MOV CL, COLS
		CHANGE1:
			MOV BL, MAX_COLS
			MOV AL, MATRIX[SI][BX]
			MOV MATRIX[SI], AL
			INC SI
			LOOP CHANGE1		
		INC J	
		MOV AL, ROWS
		SUB AX, ROW_TO_DELETE
		SUB AX, J
		MOV CX, AX
		LOOP CHANGE
	DELETE_EXIT:
		DEC ROWS
	JMP NEXT_ROW_AFTER_DELETE


; Главная
MAIN:
	; Помещаю адрес сегмента в DS
	MOV AX, DATAS
    MOV DS, AX

	; Ввод количествава строк
	MOV DX, OFFSET ROWSMSG
	CALL OUTPUT_MESSAGE
	CALL INPUT_SYMBOL
	MOV ROWS, AL
	SUB ROWS, '0'
	MOV AL, ROWS
	CALL PRINT_NEW_STR
	
	; Ввод кол-ва столбцов
	MOV DX, OFFSET COLSMSG
	CALL OUTPUT_MESSAGE
	CALL INPUT_SYMBOL
	MOV COLS, AL
	SUB COLS, '0'
	MOV AL, COLS
	CALL PRINT_NEW_STR
	
	; Ввод матрицы
	MOV DX, OFFSET MTRXMSG
	CALL OUTPUT_MESSAGE
	CALL PRINT_NEW_STR
	MOV I, 0
	MOV BX, 0
	MOV CX, 0
	MOV CL, ROWS
	INPUT1:
		MOV CL, COLS
		INPUT_ROW:
			CALL INPUT_SYMBOL			 
			MOV MATRIX[BX], AL		
			INC BX					
			CALL PRINT_SPACE	
			LOOP INPUT_ROW			
		CALL PRINT_NEW_STR
		MOV CL, ROWS
		SUB CX, I
		INC I
		MOV BL, MAX_COLS
		MOV AX, I
		MUL BX
		MOV BX, AX
		LOOP INPUT1

	; Удаление строк
	MOV I, 0 
	MOV BX, 0
	MOV CX, 0
	MOV CL, ROWS
	FOR1:
		MOV ODDCOUNT, 0
		MOV CL, COLS
		FOR2:
			MOV SI, BX
			MOV DL, COLS
			MOV DH, 0
			ADD SI, DX
			MOV AH, 0
    		MOV AL, MATRIX[BX] 
    		TEST AL, 1 
			JZ NEXT_ROW
			INC ODDCOUNT
			INC BX
			LOOP FOR2
		MOV AX, I
		MOV ROW_TO_DELETE, AX
		MOV AL, COLS
		CMP ODDCOUNT, AL
		JE DELETE_ROW
		NEXT_ROW:				
			INC I	
			NEXT_ROW_AFTER_DELETE:
				CMP ROWS, 0
				JE OUTPUT_MATRIX
				MOV AL, ROWS
				SUB AX, I
				MOV CX, AX
				INC CX
				CMP CX, 0
				JE OUTPUT_MATRIX
				MOV BL, MAX_COLS
				MOV AX, I
				MUL BX
				MOV BX, AX
		LOOP FOR1
		
	
	
	; Вывод матрицы
	OUTPUT_MATRIX:
		MOV DX, OFFSET RESLMSG
		CALL OUTPUT_MESSAGE
		CALL PRINT_NEW_STR

		CMP ROWS, 0
		JE PROGRAMM_END
		
		MOV I, 0
		MOV BX, 0
		MOV CX, 0
		MOV CL, ROWS
		OUTPUT1:
			MOV CL, COLS
			OUTPUTROW:
				MOV DL, MATRIX[BX]
				CALL OUTPUT_SYMBOL
				INC BX
				CALL PRINT_SPACE
				LOOP OUTPUTROW
			CALL PRINT_NEW_STR
			MOV CL, ROWS
			MOV SI, I
			SUB CX, SI
			INC I
			MOV BL, MAX_COLS
			MOV AX, I
			MUL BX
			MOV BX, AX
			LOOP OUTPUT1
		
		
	; Конец программы
	PROGRAMM_END:
		MOV AH,4Ch						
		INT 21h
	
CODES ENDS
END MAIN
