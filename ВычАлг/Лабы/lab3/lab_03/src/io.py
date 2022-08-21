from prettytable import PrettyTable

def input_from_file(filename):
    try:
        inputfile = open(filename, 'rt')
    except:
        return None

    else:
        xxx = []
        yyy = []
        flag = True

        while flag:
            try:
                couple = list(map(float, inputfile.readline().split()))
                xxx.append(couple[0])
                yyy.append(couple[1])
            except:
                flag = False

        if len(xxx) == 0:
            return None

        return xxx, yyy

def print_data(**kwargs):
    table = PrettyTable()
    for key in kwargs:
        table.add_column(key, kwargs[key])
    print(table)
