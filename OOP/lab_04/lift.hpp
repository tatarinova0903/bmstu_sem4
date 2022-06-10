#ifndef LIFT_H
#define LIFT_H


#include <memory>
#include <vector>
#include "controller.hpp"
#include "cabin.hpp"
#include "mybutton.h"

class Lift
{
public:
    explicit Lift(const int floors);

    const std::vector<std::shared_ptr<MyButton>> &getButtons() const;

private:
    Controller controller;
    Cabin cabin;
    std::vector<std::shared_ptr<MyButton>> buttons;
};

#endif // LIFT_H
