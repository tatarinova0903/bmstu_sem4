#ifndef CAMERA_COMMAND_H
#define CAMERA_COMMAND_H

#include <memory>
#include <cstddef>

#include "command.h"
#include "scene_manager.h"
#include "reform_manager.h"

class base_camera_command : public base_command {};

class add_camera : public base_camera_command {
public:
    add_camera(const double x,
              const double y,
              const double z);

    add_camera() = delete;

    ~add_camera() override = default;

    void execute() override;

private:
    double x_pos;
    double y_pos;
    double z_pos;
};

class remove_camera : public base_camera_command {
public:
    explicit remove_camera(const size_t &camera_n);

    remove_camera() = delete;

    ~remove_camera() override = default;

    void execute() override;

private:
    size_t camera_n;
};

class move_camera : public base_camera_command {
public:
    move_camera(const size_t &camera_n,
               const double &shift_x,
               const double &shift_y);

    move_camera() = delete;

    ~move_camera() override = default;

    void execute() override;

private:
    size_t camera_n;

    double shift_x;
    double shift_y;
};

class set_camera : public base_camera_command {
public:
    explicit set_camera(const size_t &camera_n);

    set_camera() = delete;

    ~set_camera() override = default;

    void execute() override;


private:
    size_t camera_n;
};

class count_camera : public base_camera_command {
public:
    explicit count_camera(std::shared_ptr<size_t> &c);

    count_camera() = delete;

    ~ count_camera() override = default;

    void execute() override;

private:
    std::shared_ptr<size_t> &count_;
};

#endif // CAMERA_COMMAND_H
