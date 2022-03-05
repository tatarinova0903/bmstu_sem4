#include "io.h"
#include "mainwindow.h"
#include "functions.h"
#include "action.h"
#include "controller.h"
#include <iostream>

ReturnCode download_model(figure_t &fig, action_t act)
{
    FILE *file;
    file = fopen(act.filename, "r");
    ReturnCode rc = OK;
    if (!file)
        rc = ERR_OPEN_FILE;
    else {
        rc = read_from_file(fig, file);
        fclose(file);
    }
    return rc;
}

void move(struct point &a, double dx, double dy, double dz)
{
    double x = get_point_x(a) + dx;
    double y = get_point_y(a) + dy;
    double z = get_point_z(a) + dz;
    set_point_x(a, x);
    set_point_y(a, y);
    set_point_z(a, z);
}

ReturnCode move_fig(figure_t &fig, action_t act)
{
    ReturnCode rc = OK;
    if (is_empty(fig))
    {
        rc = ERR_EMPTY;
    }
    else
    {
        double dx = get_deltax(act);
        double dy = get_deltay(act);
        double dz = get_deltaz(act);
        for (size_t i = 0; i < get_fig_n(fig); i++)
        {
            move(get_point(fig, i), dx, dy, dz);
        }
    }
    return rc;
}

void rotation_ax(struct point &a, double ax)
{
    double ya = get_point_y(a);
    double za = get_point_z(a);
    double alpha = ax * PI / 180;
    double cosa = cos(alpha);
    double sina = sin(alpha);
    double z = za * cosa + ya * sina;
    double y = za * sina + ya * cosa;
    set_point_z(a, z);
    set_point_y(a, y);
}

void rotation_ay(struct point &a, double ay)
{
    double xa = get_point_x(a);
    double za = get_point_z(a);
    double alpha = ay * PI / 180;
    double cosa = cos(alpha);
    double sina = sin(alpha);
    double x = xa * cosa + za * sina;
    double z = xa * sina + za * cosa;
    set_point_x(a, x);
    set_point_z(a, z);
}

void rotation_az(struct point &a, double az)
{
    double xa = get_point_x(a);
    double ya = get_point_y(a);
    double alpha = az * PI / 180;
    double cosaz = cos(alpha);
    double sinaz = sin(alpha);
    double x = xa * cosaz + ya * sinaz;
    double y = xa * sinaz + ya * cosaz;
    set_point_x(a, x);
    set_point_y(a, y);
}

void rotation(struct point &a, alpha_t alpha)
{
    double ax = get_alphax(alpha);
    double ay = get_alphay(alpha);
    double az = get_alphaz(alpha);
    rotation_az(a, az);
    rotation_ax(a, ax);
    rotation_ay(a, ay);
}

ReturnCode rotation_fig(figure_t &fig, action_t act)
{
    ReturnCode rc = OK;
    if (is_empty(fig))
    {
        rc = ERR_EMPTY;
    }
    else
    {
        alpha_t alpha = get_alpha(act);
        for (size_t i = 0; i < get_fig_n(fig); i++)
        {
            rotation(get_point(fig, i), alpha);
        }
    }
    return rc;
}

void scale(struct point &a, double k)
{
    double x = k * get_point_x(a);
    double y = k * get_point_y(a);
    double z = k * get_point_z(a);
    set_point_x(a, x);
    set_point_y(a, y);
    set_point_z(a, z);
}

ReturnCode scale_fig(figure_t &fig, action_t act)
{
    ReturnCode rc = OK;
    if (is_empty(fig))
    {
        rc = ERR_EMPTY;
    }
    else
    {
        double k = get_k(act);
        for (size_t i = 0; i < get_fig_n(fig); i++)
        {
            scale(get_point(fig, i), k);
        }
    }
    return rc;

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
