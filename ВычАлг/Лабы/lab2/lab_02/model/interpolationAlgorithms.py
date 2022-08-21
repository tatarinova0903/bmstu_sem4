from model import tools
import numexpr as ne
from view import graph
import matplotlib.pyplot as plt
import random


def printResultTable(table, name, flag='Newton'):
    print(name)
    print('-------------------------------------')
    for i in range(len(table)):
        if i == 0:
            print(f'X:', end=' ')
        elif i == 1:
            print(f'Y:', end=' ')
        elif i == 2 and flag == 'Hermite':
            print(f"Y':", end=' ')
        else:
            print(f'{i}:', end=' ')
        for j in range(len(table[i])):
            print('%+2.3f' % table[i][j], end=' ')
        print()
    print('-------------------------------------')


# Функция составляет полином Ньютона на основе высчитанной таблицы
def NewtonPolynomial(tableNewton):
    pol = '%2.3f' % tableNewton[1][0]
    for k in range(2, len(tableNewton)):
        pol += ' + (%2.3f' % tableNewton[k][0]
        for i in range(k - 1):
            if tableNewton[0][i] == 0:
                pol += ' * x'
            elif tableNewton[0][i] > 0:
                pol += ' * (x - %2.3f)' % tableNewton[0][i]
            else:
                pol += ' * (x + %2.3f)' % tableNewton[0][i]
        pol += ')'
    return pol


# Функция проводит одномерную интерполяцию Ньютона для выбранного значения x
# Table -- двумерный массив вида [[x][y]]
def interpolationNewton(tableNewton, x, printComments=False):
    j = 1
    while len(tableNewton[-1]) != 1:
        tableNewton.append([(tableNewton[-1][i] - tableNewton[-1][i + 1]) /
                            (tableNewton[0][i] - tableNewton[0][i + j]) for i in range(len(tableNewton[-1]) - 1)])
        j += 1

    polStr = NewtonPolynomial(tableNewton)
    pol = ne.evaluate(polStr)

    if printComments:
        printResultTable(tableNewton, "NEWTON TABLE")
        print('P(x) =', polStr)
        print('-------------------------------------')
        print('P(%2.3f) = %2.5f' % (x, ne.evaluate(polStr)))
        print('-------------------------------------')

    # возвращает таблицу значений полинома, строку-полином, итоговое значение y для введенного x
    return tableNewton, polStr, pol
