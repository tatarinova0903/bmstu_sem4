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

return_code create_arr(FILE *f, point_t *arr, size_t n)
{
    if (!f || !n || !arr)
        return ERR_INPUT;

    point_t p;
    return_code rc = OK;
    for (size_t i = 0; i < n && !rc; i++)
    {
        if (read_line_point(f, p) != OK)
        {
            rc = ERR_INPUT;
        }
        else
        {
             copy_point(arr[i], p);
        }
    }
    return rc;
}

return_code create_matrix(FILE *f, matrix_t mt, size_t n)
{
    if (!f || !n || !mt)
        return ERR_INPUT;

    int mi, mj;
    while (read_line_matrix(f, mi, mj) == OK)
    {
        mt[mi - 1][mj - 1] = 1;
        mt[mj - 1][mi - 1] = 1;
    }
    return OK;
}

return_code fill_fig(FILE *f, figure_t &fig, size_t n)
{
    return_code rc = OK;
    rc = create_arr(f, fig.arr, n);
    if (rc == OK)
    {
        rc = create_matrix(f, fig.matrix, n);
    }
    return rc;
}

size_t &get_fig_n(figure_t &fig)
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

void set_fig_matrix(figure_t &fig, matrix_t mt)
{
    fig.matrix = mt;
}

void set_fig_arr(figure_t &fig, point_t *arr)
{
    fig.arr = arr;
}

void set_fig_n(figure_t &fig, size_t n)
{
    fig.n = n;
}
