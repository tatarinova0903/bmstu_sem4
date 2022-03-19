#include "figure.h"
#include "input.h"
#include "array.h"

return_code alloc_fig(figure_t &fig, size_t n)
{
    return_code rc = allocate_arr(fig.arr, n);
    if (rc == OK)
    {
        rc = allocate_matrix(fig.matrix, n);
        if (rc != OK)
        {
            free_fig(fig);
        }
    }
    return rc;
}

return_code fill_arr(point_t *arr, size_t n, FILE *f)
{
    if (!f || !n || !arr)
    {
        return ERR_INPUT;
    }

    return_code rc = OK;
    for (size_t i = 0; !rc && i < n; i++)
    {
        if (read_line_point(arr[i], f) != OK)
        {
            rc = ERR_INPUT;
        }
    }
    return rc;
}

return_code fill_matrix(matrix_t matr, size_t n, FILE *f)
{
    if (!f || !n || !matr)
    {
        return ERR_INPUT;
    }

    return_code rc = OK;
    size_t i, j;
    while ((rc = read_line_matrix(i, j, f)) == OK && i <= n && j <= n)
    {
        matr[i - 1][j - 1] = 1;
        matr[j - 1][i - 1] = 1;
    }

    if (i > n || j > n)
    {
        rc = ERR_INPUT;
    }
    else if (feof(f))
    {
        rc = OK;
    }
    return rc;
}

return_code fill_fig(figure_t &fig, size_t n, FILE *f)
{
    return_code rc = OK;
    rc = fill_arr(fig.arr, n, f);
    if (rc == OK)
    {
        rc = fill_matrix(fig.matrix, n, f);
    }
    return rc;
}

size_t get_fig_n(figure_t fig)
{
    return fig.n;
}

int is_empty(figure_t &fig)
{
    return !(fig.arr && fig.matrix && fig.n);
}

void free_fig(figure_t &fig)
{
    if (fig.arr)
    {
        delete [] fig.arr;
    }

    if (fig.matrix)
    {
        free_matrix(fig.matrix, fig.n);
    }

    fig.n = 0;
    fig.matrix = NULL;
    fig.arr = NULL;
}

figure_t init_fig()
{
    figure_t fig;
    fig.n = 0;
    fig.matrix = NULL;
    fig.arr = NULL;
    return fig;
}

int get_matrix_el(figure_t fig, size_t i, size_t j)
{
    return fig.matrix[i][j];
}

point_t &get_array_el(figure_t &fig, size_t i)
{
    return fig.arr[i];
}

void set_fig_matrix(figure_t &fig, matrix_t matr)
{
    fig.matrix = matr;
}

void set_fig_arr(figure_t &fig, point_t *arr)
{
    fig.arr = arr;
}

void set_fig_n(figure_t &fig, size_t n)
{
    fig.n = n;
}
