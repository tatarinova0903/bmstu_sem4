#include "io.h"
#include "matrix.h"
#include "points.h"
#include "figure.h"

#include <iostream>

void rewind_file(FILE *stream)
{
    rewind(stream);
}

return_code read_line_point(FILE *f, point_t &p)
{
   int n;
   double x,y,z;
   if (fscanf(f, "%d %lf %lf %lf",&n, &x, &y, &z) == 4)
   {
       set_point(p,x,y,z,n);
       return OK;
   }
   return ERR_INPUT;
}

return_code read_line_mt_el(FILE *f, int &mi, int &mj)
{
   if (fscanf(f, "%d->%d", &mi, &mj) == 2)
       return OK;
   return ERR_INPUT;
}

return_code count_points(size_t &n, FILE *f)
{
    if (!f)
        return ERR_OPEN_FILE;
    struct point p;
    size_t num = 0;
    while (read_line_point(f,p) == OK)
    {
        num++;
    }
    rewind_file(f);
    n =  num;
    return OK;
}

return_code allocate_arr(point_t *&arr, size_t n)
{
    point_t *buf = new struct point[n];
    if (!buf)
        return ERR_MEMORY;
    arr = buf;
    return OK;
}

return_code create_arr(point_t *arr, size_t n, FILE *f)
{
    if (!f || !n || !arr)
        return ERR_INPUT;
    struct point p;

    return_code rc = OK;
    for (size_t i = 0; i < n && !rc; i++)
    {
        if (read_line_point(f,p) != OK)
        {
            rc = ERR_INPUT;
        }
        else
        {
             copy_point(arr[i],p);
        }
    }
    return rc;
}

return_code create_matrix(matrix_t mt, size_t n, FILE *f)
{
    if (!f || !n || !mt)
        return ERR_INPUT;

    int mi,mj;
    while (read_line_mt_el(f,mi,mj) == OK)
    {
        mt[mi-1][mj-1] = 1;
        mt[mj-1][mi-1] = 1;
    }
    return OK;
}

return_code alloc_fig(figure_t &fig, size_t n)
{
    return_code rc = allocate_arr(fig.arr, n);
    if (rc) return rc;
    rc = allocate_matrix(fig.matrix, n);
    if (rc)
    {
        free_fig(fig);
    }
    return rc;
}

return_code create_fig(figure_t &fig, size_t n, FILE *f)
{
    return_code rc = OK;
    rc = create_arr(fig.arr, n, f);
    if (rc) return rc;
    rc = create_matrix(fig.matrix,n,f);
    return rc;
}

return_code read_from_file(figure &fig, FILE *f)
{
    return_code rc = OK;
    if (!f)
    {
        rc = ERR_EMPTY;
    }
    else {
        free_fig(fig);
        fig = init_fig();

        rc = count_points(fig.n, f);
        if (rc == OK)
        {
            rc = alloc_fig(fig, fig.n);
            if (rc == OK)
            {
                rc = create_fig(fig, fig.n, f);
                if (rc != OK)
                {
                 free_fig(fig);
                }
            }
        }
    }
    return rc;
}
