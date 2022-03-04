#include "io.h"
#include "mainwindow.h"
#include "functions.h"
#include "action.h"
#include "controller.h"
#include <iostream>

ReturnCode download_model(figure_t &fig, action_t act)
{
    const char *filename = "/Users/daria/Desktop/sem4/OOP/lab01/1.txt";
    stream_t stream;
    ReturnCode rc = open_file_read(stream, filename, FILE_OPEN_TYPE);
    if (rc)
        return rc;
    rc = read_from_file(fig,stream);
    close_file(stream);
    return rc;
}

void move(struct point &a, double dx, double dy, double dz)
{
    double x = get_point_x(a) + dx;
    double y = get_point_y(a) + dy;
    double z = get_point_z(a) + dz;
    set_point_x(a,x);
    set_point_y(a,y);
    set_point_z(a,z);
}

ReturnCode move_fig(figure_t &fig, action_t act)
{
    if (is_empty(fig))
        return ERR_EMPTY;
    double dx = get_deltax(act);
    double dy = get_deltay(act);
    double dz = get_deltaz(act);
    for (size_t i = 0; i < get_fig_n(fig); i++)
    {
        move(get_point(fig,i),dx,dy,dz);
    }
    return OK;
}

void rotation_ax(struct point &a, struct point center, double ax)
{
    double yc = get_point_y(center);
    double zc = get_point_z(center);
    double ya = get_point_y(a);
    double za = get_point_z(a);
    double alpha = ax * PI / 180;
    double cosa = cos(alpha);
    double sina = sin(alpha);
    double z = zc + (za- zc) * cosa + (ya - yc) * sina;
    double y = yc - (za - zc) * sina + (ya - yc) * cosa;
    set_point_z(a,z);
    set_point_y(a,y);
}

void rotation_ay(struct point &a, struct point center, double ay)
{

    double xc = get_point_x(center);
    double zc = get_point_z(center);
    double xa = get_point_x(a);
    double za = get_point_z(a);
    double alpha = ay * PI / 180;
    double cosa = cos(alpha);
    double sina = sin(alpha);
    double x = xc + (xa- xc) * cosa + (za - zc) * sina;
    double z = zc - (xa - xc) * sina + (za - zc) * cosa;
    set_point_x(a,x);
    set_point_z(a,z);
}
void rotation_az(struct point &a, struct point center, double az)
{
    double xc = get_point_x(center);
    double yc = get_point_y(center);
    double xa = get_point_x(a);
    double ya = get_point_y(a);
    double alpha = az * PI / 180;
    double cosaz = cos(alpha);
    double sinaz = sin(alpha);

    double x = xc + (xa- xc) * cosaz + (ya - yc) * sinaz;
    double y = yc - (xa - xc) * sinaz + (ya - yc) * cosaz;
    set_point_x(a,x);
    set_point_y(a,y);
}
void rotation(struct point &a, struct point c, alpha_t alpha)
{
    double ax = get_alphax(alpha);
    double ay = get_alphay(alpha);
    double az = get_alphaz(alpha);
    if (az != 0)
    {
         rotation_az(a,c,az);
    }
    if (ax != 0)
    {
         rotation_ax(a,c,ax);
    }
    if (ay != 0)
    {
         rotation_ay(a,c,ay);
    }
}

ReturnCode rotation_fig(figure_t &fig, action_t act)
{
    if (is_empty(fig))
        return ERR_EMPTY;
    alpha_t alpha = get_alpha(act);

    struct point center;
    set_point(center,0,0,0,0);
    for (size_t i = 0; i < get_fig_n(fig); i++)
    {
        rotation(get_point(fig,i),center,alpha);
    }
    return OK;
}

void scale(struct point &a, struct point center, double k)
{
    double xc = get_point_x(center);
    double yc = get_point_y(center);
    double zc = get_point_z(center);

    double x = xc + k*(get_point_x(a) - xc);
    double y = yc + k*(get_point_y(a) - yc);
    double z = zc + k*(get_point_z(a) - zc);
    set_point_x(a,x);
    set_point_y(a,y);
    set_point_z(a,z);
}

ReturnCode scale_fig(figure_t &fig, action_t act)
{
    if (is_empty(fig))
        return ERR_EMPTY;
    double k = get_k(act);
    struct point center;
    set_point(center,0,0,0,0);
    for (size_t i = 0; i < get_fig_n(fig); i++)
    {
        scale(get_point(fig,i),center,k);
    }
    return OK;

}

ReturnCode clear_fig(figure_t &fig)
{
    free_fig(fig);
    return OK;
}

ReturnCode draw_fig(figure_t &fig, myscene_t scene)
{
    clear_scene(scene);
    draw_model(fig,scene);
    return OK;
}

void draw_model(figure_t fig, myscene_t scene)
{
    if (is_empty(fig))
        return;

    for (size_t i = 0; i < get_fig_n(fig); i++)
    {
        draw_point_scene(scene, get_point(fig,i));
    }
    for (size_t i = 0; i < get_fig_n(fig); i++)
    {
        for (size_t j = 0; j < i + 1; j++)
        {
            if (get_matrix_el(fig,i,j) != 0)
            {
                draw_line_scene(scene, get_point(fig,i), get_point(fig,j));
            }
        }
    }
}
