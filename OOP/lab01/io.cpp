#include "io.h"
#include "matrix.h"
#include "points.h"
#include "figure.h"

#include <iostream>



ReturnCode open_file_read(stream_t &stream, const char *filename, const char *open_type)
{
     FILE *file = fopen(filename, open_type);
     if (!file)
     {
         return ERR_OPEN_FILE;
     }
     stream  = file;
     return OK;
}
void close_file(stream_t stream)
{
    fclose(stream);
}

void rewind_file(stream_t stream)
{
    rewind(stream);
}

ReturnCode read_line_point(stream_t f, point_t &p)
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

ReturnCode read_line_mt_el(stream_t f, int &mi, int &mj)
{
   if (fscanf(f, "%d->%d",&mi, &mj) == 2)
       return OK;
   return ERR_INPUT;
}

ReturnCode count_points(size_t &n, stream_t f)
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

ReturnCode allocate_arr(point_t *&arr, size_t n)
{
    point_t *buf = new struct point[n];
    if (!buf)
        return ERR_MEMORY;
    arr = buf;
    return OK;
}

ReturnCode create_arr(point_t *arr, size_t n, stream_t f)
{
    if (!f || !n || !arr)
        return ERR_INPUT;
    struct point p;

    ReturnCode rc = OK;
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

ReturnCode create_matrix(matrix_t mt, size_t n, stream_t f)
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

ReturnCode alloc_fig(figure_t &fig, size_t n)
{
    ReturnCode rc = allocate_arr(fig.arr, n);
    if (rc) return rc;
    rc = allocate_matrix(fig.matrix, n);
    if (rc)
    {
        free_fig(fig);
    }
    return rc;
}

ReturnCode create_fig(figure_t &fig, size_t n, stream_t f)
{
    ReturnCode rc = OK;
    rc = create_arr(fig.arr, n, f);
    if (rc) return rc;
    rc = create_matrix(fig.matrix,n,f);
    return rc;
}

ReturnCode read_from_file(figure &fig, stream_t f)
{
    if (!f)
        return ERR_EMPTY;

    ReturnCode rc = OK;
    figure work = init_fig();

    rc = count_points(work.n, f);
    if (rc) return rc;
    rc = alloc_fig(work, work.n);
    if (rc) return rc;

    rc = create_fig(work, work.n, f);

    if (rc == OK)
    {
         free_fig(fig);
         copy_fig(fig,work);
    }
    else
    {
        free_fig(work);
    }
    return rc;
}
