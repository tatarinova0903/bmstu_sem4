from turtle import color
from matplotlib.pyplot import *
import random as rd 
from numpy import linspace

# Кол-во точек в таблице
LEN_T = 20

# Функция для заполнения таблицы рандомными значениями
def table_():
    x_arr = []
    y_arr = []
    w_arr = []
    for i in range(LEN_T):
        x = rd.random() * 10
        y = rd.random() * 10
        x_arr.append(x)
        y_arr.append(y)
        w_arr.append(1)
    return x_arr, y_arr, w_arr

# Печать таблицы
def print_table(x, y, ro):
    length = len(x)
    print("|    №   |    x    |    y    |    P    |")
    with open('in_2d.txt', 'w') as f:
        f.write('[')
        for i in range(length):
            print("|{:^8d}| {:^7.4f} | {:^7.4f} | {:^7.4f} |".format(i + 1, x[i], y[i], ro[i]))
            f.write("[{:f}, {:f}, {:f}],\\\n".format(x[i], y[i], ro[i]))
        f.write(']')
    print()


# Матрица СЛАУ
def makeSLAEmatrix(matrix, n):
    N = len(matrix)
    res = [[0 for i in range(0, n + 1)] for j in range(0, n + 1)]
    col = [0 for i in range(0, n + 1)]
    for i in range(0, n + 1):
        for j in range(N):
            coef = matrix[j][2] * matrix[j][0] ** i
            for k in range(0, n + 1):
                res[i][k] += coef * matrix[j][0] ** k
            col[i] += coef * matrix[j][1]
    for i in range(len(col)):
        res[i].append(col[i])
    return res

# Функция метод Гаусс
def method_gauss(mat, degree):
    matrix = makeSLAEmatrix(mat, degree)

    n = len(matrix)
    # print(matrix)
    for k in range(n):
        for i in range(k + 1, n):
            coeff = -(matrix[i][k] / matrix[k][k])
            for j in range(k, n + 1):
                matrix[i][j] += coeff * matrix[k][j]
    a = [0 for i in range(n)]
    for i in range(n - 1, -1, -1):
        for j in range(n - 1, i, -1):
            matrix[i][n] -= a[j] * matrix[i][j]
        print(matrix[i][i])
        a[i] = matrix[i][n] / matrix[i][i]
    return a

# Главная функция
def main():
    table = [[2, 7, 1.000000],\
            [5, 6, 100.000000],\
            [3, 5, 1.000000],\
            [3, 6, 1.000000],\
            [4, 7, 1.000000],\
            [1, 6, 1.000000],\
            # [8.791141, 0.679420, 1.000000],\
            # [1.271486, 7.616962, 1.000000],\
            # [3.790363, 8.865701, 1.000000],\
            # [0.030792, 9.856635, 1.000000],\
            # [3.714665, 6.832725, 1.000000],\
            # [3.364756, 3.337023, 1.000000],\
            # [3.802367, 7.460081, 1.000000],\
            # [4.598829, 4.103456, 1.000000],\
            [7, 8, 100.000000],\
            # [7.029809, 8.266240, 1.000000],\
            # [5.649645, 7.990988, 1.000000],\
            # [2.119824, 1.217195, 1.000000],\
            # [0.694963, 8.175096, 1.000000],\
            # [4.724728, 5.946820, 1.000000],\
            ]
    x_coor = [table[i][0] for i in range(len(table))]
    y_coor = [table[i][1] for i in range(len(table))]
    w = [table[i][2] for i in range(len(table))]

    print("Принятая таблица:\n")
    print_table(x_coor, y_coor, w)

    degree = -1
    while degree < 0:
        try:
            degree = int(input("Введите степень аппроксимирующего полинома: "))
        except Exception:
            print("Неверная степень. Степень должна быть больше 0.\n")

    plot(x_coor, y_coor, 'o', label='Point', color="green")
    answer = method_gauss(table, degree)
    print("Коэффициенты А полинома: ", answer)
    x = list(linspace(min(x_coor), max(x_coor), 50))
    res_x = []
    res_y = []
    for elx in x:
        ely = 0
        for k in range(0, degree + 1):
            ely += answer[k] * elx ** k
        res_x.append(elx)
        res_y.append(ely)
    plot(res_x, res_y, label='n = ' + str(degree), color="blue")
    legend()
    title("Двумерная аппроксимация")
    grid()
    xlabel('x')
    ylabel('y')
    show()

main()