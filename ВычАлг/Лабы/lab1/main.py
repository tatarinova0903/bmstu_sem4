# Считывание таблицы
def input_table():
    table = []
    f = open('input.txt', 'r')
    j = 0
    for line in f:
        nums = line.split(" ")
        table.append([0] * 3)
        for i in range(3):
            table[j][i] = float(nums[i])
        j += 1
    f.close()
    
    # for row in table:
    #     print(row)
    # print("")
    coords_x = []
    coords_y = []
    for i in range(len(table)):
        coords_x.append(table[i][0])
        coords_y.append(table[i][1])
    return table, coords_x, coords_y

# Нахождение начального и конечного индекса в таблице
def find_x_start_x_end(data, n, arg):
    index_x = 0
    
    while arg > data[index_x][0]:
        index_x += 1
    index_x_start = index_x - n // 2 - 1
    index_x_end = index_x + (n // 2) + (n % 2) - 1
    if index_x_end > len(data) - 1:
        index_x_start -= index_x_end - len(data) + 1
        index_x_end = len(data) - 1
    elif index_x_start < 0:
        index_x_end += -index_x_start
        index_x_start = 0
    return index_x_start, index_x_end

# Расчет разделенных разностей
def div_diff(x, y, n):
    pol = []
    for i in range(n):
        pol.append([0] * (n + 1))
    for i in range(n):
        pol[i][0], pol[i][1] = x[i], y[i]
    i = 2
    new_node = n - 1
    while i < (node + 1):
        j = 0
        while j < new_node:
            pol[j][i] = round((pol[j + 1][i - 1] - pol[j][i - 1]) \
                 / (pol[i - 1][0] - pol[0][0]), 5)
            j += 1
        i += 1
        new_node -= 1
    return pol

# Полином Ньютона
def polinom_newton(coords_x, coords_y, n, arg):
    pol = div_diff(coords_x, coords_y, n)
    y = pol[0][1]
    i = 2
    while i < n + 1:
        j = 0
        p = 1
        while j < i - 1:
            p *= (arg - pol[j][0])
            j += 1
        y += pol[0][i] * p
        i += 1
    return y 

# Полином Эрмита
def polinom_hermit(data, n, arg, coord_x):
    return 0


def main():
    table, coords_x, coords_y = input_table()
    x = float(input("Введите x: "))
    arr_n = [1, 2, 3, 4, 5]

    print("| n |   x   |  Ньютон  |   Эрмит  |")
    for n in arr_n:
        print("| {} | {:5.3f} |".format(n, x), end="")
        flag = 0
        for i in range(0, len(table)):
            if x == table[i][0]:
                print("  {:7.3f} |  {:7.3f} |\n".format(table[i][1], table[i][1]))
                flag = 1
        if flag == 0:
            x_start, x_end = find_x_start_x_end(table, n, x)
            coords_x_for_polinom = coords_x[x_start : x_end + 1]
            coords_y_for_polinom = coords_y[x_start : x_end + 1]
            
            res_new = polinom_newton(coords_x_for_polinom, coords_y_for_polinom, n + 1, x)
            res_herm = polinom_hermit(table, n + 1, x, coords_x)

            print("  {:7.3f} |  {:7.3f} |".format(res_new, res_herm)) 

    print("\nОбратная инерполяция\n")
    print("| n |  x  |    y     |")

    for n in arr_n:  
        print("| {} |  {}  |".format(n, 0), end="")
        flag = 0
        for i in range(0, len(table)):
            if 0 == table[i][1]:
                print("Данное значение x есть в таблице, F(x) = ", table[i][0], sep="")
                flag = 1
        if flag == 0:
            n_data = []
            for i in range(len(table)):
                n_data.append([table[i][1], table[i][0], table[i][2]])
            n_data.sort()
            coords_y.clear()
            coords_x.clear()
            for i in range(len(n_data)):
                coords_x.append(n_data[i][0])
                coords_y.append(n_data[i][1])
            x0, xn = find_x_start_x_end(n_data, n, 0)
            ax = coords_x[x0 : xn + 1]
            ay = coords_y[x0 : xn + 1]
            if len(ax):
                res = polinom_newton(ax, ay, n + 1, 0)
                print("  {:7.3f} |".format(res)) 

if __name__ == "__main__":
    main()
