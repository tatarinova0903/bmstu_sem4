#ifndef MODEL_COMMAND_H
#define MODEL_COMMAND_H

#include <memory>
#include <cstddef>

#include "command.h"
#include "model.h"

class base_model_command: public base_command
{};

class move_model: public base_model_command
{
public:
    move_model(const double &dx,
              const double &dy,
              const double &dz,
               std::shared_ptr<object> object);

    move_model() = delete;

    ~move_model() override = default;

    void execute() override;

private:
    double dx, dy, dz;
    std::shared_ptr<object> model;
};

class scale_model: public base_model_command
{
public:
    scale_model(const double &x,
               const double &y,
               const double &z,
                std::shared_ptr<object> model);

    scale_model() = delete;

    ~scale_model() override = default;

    void execute() override;

private:
    double kx, ky, kz;
    std::shared_ptr<object> model;
};

class rotate_model: public base_model_command
{
public:
    rotate_model(const double &ox,
              const double &oy,
              const double &oz,
                 std::shared_ptr<object> model);

    rotate_model() = delete;

    ~rotate_model() override = default;

    void execute() override;

private:
    double ox, oy, oz;
    std::shared_ptr<object> model;
};

class reform_model: public base_model_command
{
public:
    reform_model(const point &m,
                const point &s,
                const point &t,
                 std::shared_ptr<object> model);

    reform_model() = delete;
    ~reform_model() override = default;

    void execute() override;

private:
    point move, scale, turn;
    std::shared_ptr<object> model;
};


class load_model : public base_model_command {
public:
    explicit load_model(std::string file_name);

     load_model() = delete;

    ~load_model() override = default;

    void execute() override;

private:
    std::string file_name;
};



class add_model : public base_model_command {
public:
    explicit add_model(std::shared_ptr<object> model);


    add_model() = delete;
    ~add_model() override = default;

    void execute() override;

private:
    std::shared_ptr<object> model;
};


class remove_model : public base_model_command {
public:
    remove_model() = delete;

    explicit remove_model(const size_t &model_n);

    ~remove_model() override = default;

    void execute() override;

private:
    size_t model_n_;
};


class count_model : public base_model_command {
public:
    count_model() = delete;

    explicit count_model(std::shared_ptr<size_t> &c);



    ~count_model() override = default;

    void execute() override;

private:
    std::shared_ptr<size_t> &count_;
};


#endif // MODEL_COMMAND_H

