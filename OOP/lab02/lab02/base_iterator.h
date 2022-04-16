#ifndef BASE_ITERATOR_H
#define BASE_ITERATOR_H

#include <time.h>
#include <memory>
#include "exceptions.h"
#include "node.h"

using namespace std;

namespace iterator
{
    template<typename T>
    class base_iterator
    {
    public:
        base_iterator(const base_iterator<T>&);
        base_iterator(shared_ptr<Node<T>> ptr);
        base_iterator(weak_ptr<Node<T>> ptr);
        virtual ~base_iterator();

        base_iterator<T>&operator =(const base_iterator<T>&);

        base_iterator<T>& operator ++();
        base_iterator<T> operator ++(int);

        operator bool() const;
        bool operator ==(const base_iterator<T>&) const;
        bool operator !=(const base_iterator<T>&) const;
    protected:
        weak_ptr<Node<T>> ptr;
    };
}

#include "base_iterator_implement.hpp"
#endif // BASE_ITERATOR_H
