#ifndef LIST_ITERATOR_H
#define LIST_ITERATOR_H
#include "base_iterator.h"

using namespace iterator;
using namespace std;

template<typename T>
class list_iterator : public base_iterator<T>
{
public:
    list_iterator(const list_iterator<T>&);
    list_iterator(shared_ptr<Node<T>> ptr);
    T& operator *();
    shared_ptr<Node<T>> operator ->();
    const T &operator *() const;
    const shared_ptr<Node<T>>  operator ->() const;
};

#include "list_iterator_implement.hpp"
#endif // LIST_ITERATOR_H
